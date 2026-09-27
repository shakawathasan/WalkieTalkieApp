package com.example.walkietalkie.data.nearby

import android.content.Context
import android.util.Log
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import com.example.walkietalkie.domain.model.ConnectionQuality
import com.example.walkietalkie.domain.model.NearbyPeer
import com.example.walkietalkie.domain.model.NetworkPacket
import com.example.walkietalkie.domain.model.PacketType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.util.UUID

/**
 * Wraps Google's Nearby Connections API (P2P_STAR strategy) to give us:
 *  - device discovery (advertise + discover)
 *  - direct connections between two phones
 *  - a basic relay: if we're connected to endpoints that aren't the final
 *    destination, we forward the packet on, decrementing hop budget.
 *
 * This is real networking (not simulated). What it does NOT do out of the box:
 *  - true mesh topology tracking across many devices (kept intentionally simple —
 *    see RelayManager for where to extend routing decisions)
 *  - end-to-end payload encryption (add before shipping — see section 13 of the spec)
 */
class NearbyManager(
    private val context: Context,
    private val localDeviceId: String,
    private val localDisplayName: String
) {
    private val serviceId = "com.example.walkietalkie.SERVICE"
    private val client = Nearby.getConnectionsClient(context)

    // endpointId -> peer info
    private val _peers = MutableStateFlow<Map<String, NearbyPeer>>(emptyMap())
    val peers: StateFlow<Map<String, NearbyPeer>> = _peers.asStateFlow()

    private val _incomingPackets = MutableSharedFlow<NetworkPacket>(extraBufferCapacity = 64)
    val incomingPackets: SharedFlow<NetworkPacket> = _incomingPackets

    // dedupe cache: messageId -> seen. Prevents relay loops / duplicate delivery (spec section 12/36).
    private val seenMessageIds = object : LinkedHashMap<String, Long>(256, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, Long>?): Boolean =
            size > 500
    }

    private val connectedEndpoints = mutableSetOf<String>()

    fun startAdvertising() {
        val options = AdvertisingOptions.Builder().setStrategy(Strategy.P2P_STAR).build()
        client.startAdvertising(
            localDisplayName, serviceId, connectionLifecycleCallback, options
        ).addOnFailureListener { Log.e(TAG, "advertise failed", it) }
    }

    fun startDiscovery() {
        val options = DiscoveryOptions.Builder().setStrategy(Strategy.P2P_STAR).build()
        client.startDiscovery(serviceId, endpointDiscoveryCallback, options)
            .addOnFailureListener { Log.e(TAG, "discovery failed", it) }
    }

    fun stopAll() {
        client.stopAdvertising()
        client.stopDiscovery()
        client.stopAllEndpoints()
        connectedEndpoints.clear()
        _peers.value = emptyMap()
    }

    fun requestConnection(endpointId: String) {
        client.requestConnection(localDisplayName, endpointId, connectionLifecycleCallback)
            .addOnFailureListener { Log.e(TAG, "requestConnection failed", it) }
    }

    fun disconnect(endpointId: String) {
        client.disconnectFromEndpoint(endpointId)
        connectedEndpoints.remove(endpointId)
        _peers.value = _peers.value - endpointId
    }

    /** Send a packet to a specific known destination. If we have no direct link to it,
     *  flood it to all connected trusted endpoints so they can relay it onward. */
    fun send(packet: NetworkPacket) {
        val bytes = encode(packet)
        val directEndpoint = connectedEndpoints.firstOrNull { endpointIdToDeviceId[it] == packet.destinationId }
        val targets = if (directEndpoint != null) listOf(directEndpoint) else connectedEndpoints.toList()
        if (targets.isEmpty()) return // caller should mark message PENDING
        client.sendPayload(targets, Payload.fromBytes(bytes))
    }

    private val endpointIdToDeviceId = mutableMapOf<String, String>()

    private val connectionLifecycleCallback = object : ConnectionLifecycleCallback() {
        override fun onConnectionInitiated(endpointId: String, info: ConnectionInfo) {
            // Section 10: never auto-accept unknown devices — surface to UI for explicit accept.
            pendingConnections[endpointId] = info
            _pendingConnectionRequests.tryEmit(endpointId to info.endpointName)
        }

        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            if (result.status.isSuccess) {
                connectedEndpoints.add(endpointId)
                updatePeer(endpointId, isDirect = true, quality = ConnectionQuality.STRONG)
            }
        }

        override fun onDisconnected(endpointId: String) {
            connectedEndpoints.remove(endpointId)
            _peers.value = _peers.value - endpointId
        }
    }

    private val pendingConnections = mutableMapOf<String, ConnectionInfo>()
    private val _pendingConnectionRequests = MutableSharedFlow<Pair<String, String>>(extraBufferCapacity = 16)
    val pendingConnectionRequests: SharedFlow<Pair<String, String>> = _pendingConnectionRequests

    fun acceptConnection(endpointId: String) {
        client.acceptConnection(endpointId, payloadCallback)
    }

    fun rejectConnection(endpointId: String) {
        client.rejectConnection(endpointId)
        pendingConnections.remove(endpointId)
    }

    private val endpointDiscoveryCallback = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
            updatePeer(endpointId, isDirect = false, quality = ConnectionQuality.MEDIUM, name = info.endpointName)
        }

        override fun onEndpointLost(endpointId: String) {
            _peers.value = _peers.value - endpointId
        }
    }

    private val payloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload) {
            val bytes = payload.asBytes() ?: return
            val packet = decode(bytes) ?: return

            if (seenMessageIds.containsKey(packet.messageId)) return // duplicate — drop (section 12)
            if (packet.isExpired()) return // expired — drop (section 36)
            seenMessageIds[packet.messageId] = System.currentTimeMillis()

            _incomingPackets.tryEmit(packet)

            val isForUs = packet.destinationId == localDeviceId
            if (!isForUs && packet.canRelayFurther()) {
                val relayed = packet.copy(hopCount = packet.hopCount + 1)
                send(relayed) // forward toward destination (or flood) — RelayManager decides policy upstream
            }
        }

        override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) {}
    }

    private fun updatePeer(
        endpointId: String,
        isDirect: Boolean,
        quality: ConnectionQuality,
        name: String? = null
    ) {
        val existing = _peers.value[endpointId]
        val peer = NearbyPeer(
            endpointId = endpointId,
            deviceId = endpointIdToDeviceId[endpointId] ?: endpointId,
            displayName = name ?: existing?.displayName ?: endpointId,
            isDirect = isDirect,
            hopCount = if (isDirect) 0 else (existing?.hopCount ?: 1),
            quality = quality,
            trusted = existing?.trusted ?: false
        )
        _peers.value = _peers.value + (endpointId to peer)
    }

    private fun encode(packet: NetworkPacket): ByteArray {
        val header = "${packet.messageId}|${packet.senderId}|${packet.destinationId}|" +
            "${packet.timestamp}|${packet.hopCount}|${packet.maxHops}|${packet.routeId}|${packet.packetType.name}|"
        val headerBytes = header.toByteArray(StandardCharsets.UTF_8)
        val buffer = ByteBuffer.allocate(4 + headerBytes.size + packet.payload.size)
        buffer.putInt(headerBytes.size)
        buffer.put(headerBytes)
        buffer.put(packet.payload)
        return buffer.array()
    }

    private fun decode(bytes: ByteArray): NetworkPacket? = try {
        val buffer = ByteBuffer.wrap(bytes)
        val headerLen = buffer.int
        val headerBytes = ByteArray(headerLen)
        buffer.get(headerBytes)
        val payload = ByteArray(buffer.remaining())
        buffer.get(payload)
        val parts = String(headerBytes, StandardCharsets.UTF_8).split("|")
        NetworkPacket(
            messageId = parts[0],
            senderId = parts[1],
            destinationId = parts[2],
            timestamp = parts[3].toLong(),
            hopCount = parts[4].toInt(),
            maxHops = parts[5].toInt(),
            routeId = parts[6],
            packetType = PacketType.valueOf(parts[7]),
            payload = payload
        )
    } catch (e: Exception) {
        Log.e(TAG, "decode failed", e)
        null
    }

    companion object {
        private const val TAG = "NearbyManager"
        fun newMessageId(): String = UUID.randomUUID().toString()
    }
}
