package com.example.walkietalkie.domain.transport

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.example.walkietalkie.data.local.dao.MessageDao
import com.example.walkietalkie.data.nearby.NearbyManager
import com.example.walkietalkie.data.remote.OnlineDataSource
import com.example.walkietalkie.data.remote.OnlineState
import com.example.walkietalkie.domain.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.nio.charset.StandardCharsets

/**
 * Single source of truth for "how do we reach the outside world right now".
 * UI/ViewModels never touch NearbyManager or OnlineDataSource directly — they
 * go through here, matching the layering the spec calls for in section 5/6.
 *
 * Routing priority (section 6/43): Online -> Nearby Direct -> Nearby Relay -> Pending.
 */
class CommunicationTransportManager(
    private val context: Context,
    private val localDeviceId: String,
    private val nearbyManager: NearbyManager,
    private val onlineDataSource: OnlineDataSource,
    private val messageDao: MessageDao,
    private val scope: CoroutineScope
) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _internetAvailable = MutableStateFlow(false)
    val internetAvailable: StateFlow<Boolean> = _internetAvailable.asStateFlow()

    val currentRoute: StateFlow<TransportType> = combine(
        _internetAvailable, onlineDataSource.state, nearbyManager.peers
    ) { internet, onlineState, peers ->
        when {
            internet && onlineState == OnlineState.CONNECTED -> TransportType.ONLINE
            peers.values.any { it.isDirect } -> TransportType.NEARBY_DIRECT
            peers.values.isNotEmpty() -> TransportType.NEARBY_RELAY
            else -> TransportType.PENDING
        }
    }.stateIn(scope, SharingStarted.Eagerly, TransportType.PENDING)

    fun start() {
        registerNetworkCallback()
        nearbyManager.startAdvertising()
        nearbyManager.startDiscovery()
        onlineDataSource.connect()
        observeIncoming()
        observePendingQueueDrain()
    }

    fun stop() {
        nearbyManager.stopAll()
        onlineDataSource.disconnect()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    /** Send text/vibration/voice. Always writes to Room first (offline-first, section 5/25). */
    suspend fun sendPacket(destinationId: String, packetType: PacketType, payload: ByteArray, messageId: String) {
        val route = currentRoute.value
        val packet = NetworkPacket(
            messageId = messageId,
            senderId = localDeviceId,
            destinationId = destinationId,
            timestamp = System.currentTimeMillis(),
            hopCount = 0,
            maxHops = 5,
            routeId = messageId,
            packetType = packetType,
            payload = payload
        )
        when (route) {
            TransportType.ONLINE -> {
                val ok = onlineDataSource.send(encodeForOnline(packet))
                messageDao.updateStatus(messageId, if (ok) com.example.walkietalkie.domain.model.MessageStatus.SENT else com.example.walkietalkie.domain.model.MessageStatus.PENDING)
            }
            TransportType.NEARBY_DIRECT, TransportType.NEARBY_RELAY -> {
                nearbyManager.send(packet)
                messageDao.updateStatus(messageId, com.example.walkietalkie.domain.model.MessageStatus.SENT)
            }
            TransportType.PENDING -> {
                messageDao.updateStatus(messageId, com.example.walkietalkie.domain.model.MessageStatus.PENDING)
            }
        }
    }

    private fun encodeForOnline(packet: NetworkPacket): ByteArray {
    val rawString = "${packet.messageId}|${packet.senderId}|${packet.destinationId}|${packet.packetType.name}|" +
            String(packet.payload, StandardCharsets.UTF_8)
    return rawString.toByteArray(StandardCharsets.UTF_8)
}

    private fun observeIncoming() {
        scope.launch { nearbyManager.incomingPackets.collect { onPacketReceived(it) } }
        scope.launch {
            onlineDataSource.incomingBytes.collect { bytes ->
                // Parse according to your backend's wire format; left as an extension point.
            }
        }
    }

    private suspend fun onPacketReceived(packet: NetworkPacket) {
        // Persisted by the repository layer normally; kept minimal here.
    }

    /** Section 18/25: whenever a route becomes available, retry anything PENDING. */
    private fun observePendingQueueDrain() {
        scope.launch {
            currentRoute.collect { route ->
                if (route != TransportType.PENDING) {
                    val pending = messageDao.getByStatus(com.example.walkietalkie.domain.model.MessageStatus.PENDING)
                    pending.forEach { msg ->
                        sendPacket(msg.receiverId, PacketType.TEXT, msg.content.toByteArray(StandardCharsets.UTF_8), msg.messageId)
                    }
                }
            }
        }
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) { _internetAvailable.value = true }
        override fun onLost(network: Network) { _internetAvailable.value = false }
        override fun onCapabilitiesChanged(network: Network, caps: NetworkCapabilities) {
            _internetAvailable.value = caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        }
    }

    private fun registerNetworkCallback() {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(request, networkCallback)
    }
}
