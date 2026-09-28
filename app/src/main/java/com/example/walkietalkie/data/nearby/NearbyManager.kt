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

class NearbyManager(
    private val context: Context,
    private val localDeviceId: String,
    private val localDisplayName: String
) {
    private val serviceId = "com.example.walkietalkie.SERVICE"
    private val client = Nearby.getConnectionsClient(context)

    private val _peers = MutableStateFlow<Map<String, NearbyPeer>>(emptyMap())
    val peers: StateFlow<Map<String, NearbyPeer>> = _peers.asStateFlow()

    private val _incomingPackets = MutableSharedFlow<NetworkPacket>(extraBufferCapacity = 64)
    val incomingPackets: SharedFlow<NetworkPacket> = _incomingPackets

    private val seenMessageIds = object : LinkedHashMap<String, Long>(256, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, Long>?): Boolean =
            size > 500
    }

    private val connectedEndpoints = mutableSetOf<String>()
    private val endpointIdToDeviceId = mutableMapOf<String, String>()

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
        endpointIdToDeviceId.clear()
        _peers.value = emptyMap()
    }

    fun requestConnection(endpointId: String) {
        client.requestConnection(localDisplayName, endpointId, connectionLifecycleCallback)
            .addOnFailureListener { Log.e(TAG, "requestConnection failed to $endpointId", it) }
    }

    fun disconnect(endpointId: String) {
        client.disconnectFromEndpoint(endpointId)
        connectedEndpoints.remove(endpointId)
        _peers.value = _peers.value - endpointId
    }

    fun send(packet: NetworkPacket) {
        val bytes = encode(packet)
        val directEndpoint = connectedEndpoints.firstOrNull { endpointIdToDeviceId[it] == packet.destinationId }
        val targets = if (directEndpoint != null) listOf(directEndpoint) else connectedEndpoints.toList()
        if (targets.isEmpty()) return
        client.sendPayload(targets, Payload.fromBytes(bytes))
    }

    private val connectionLifecycleCallback = object : ConnectionLifecycleCallback() {
        override fun onConnectionInitiated(endpointId: String, info: ConnectionInfo) {
            // FIX: Auto-accept connection requests so walkie talkies pair automatically
            client.acceptConnection(endpointId, payloadCallback)
        }

        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            if (result.status.isSuccess) {
                connectedEndpoints.add(endpointId)
                updatePeer(endpointId, isDirect = true, quality = ConnectionQuality.STRONG)
                Log.d(TAG, "Successfully connected to endpoint: $endpointId")
            } else {
                Log.e(TAG, "Connection failed to endpoint: $endpointId with status ${result.status.statusCode}")
            }
        }

        override fun onDisconnected(endpointId: String) {
            connectedEndpoints.remove(endpointId)
            _peers.value = _peers.value - endpointId
            Log.d(TAG, "Disconnected from endpoint: $endpointId")
        }
    }

    private val endpointDiscoveryCallback = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
            Log.d(TAG, "Endpoint found: $endpointId (${info.endpointName})")
            updatePeer(endpointId, isDirect = false, quality = ConnectionQuality.MEDIUM, name = info.endpointName)
            
            // FIX: Automatically initiate connection when an endpoint is discovered
            requestConnection(endpointId)
        }

        override fun onEndpointLost(endpointId: String) {
            _peers.value = _peers.value - endpointId
        }
    }

    private val payloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload) {
            val bytes = payload.asBytes() ?: return
            val packet = decode(bytes) ?: return

            if (seenMessageIds.containsKey(packet.messageId)) return
            if (packet.isExpired()) return
            seenMessageIds[packet.messageId] = System.currentTimeMillis()

            _incomingPackets.tryEmit(packet)

            val isForUs = packet.destinationId == localDeviceId
            if (!isForUs && packet.canRelayFurther()) {
                val relayed = packet.copy(hopCount = packet.hopCount + 1)
                send(relayed)
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
            trusted = existing?.trusted ?: true
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
