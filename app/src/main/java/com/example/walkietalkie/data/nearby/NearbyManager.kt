package com.example.walkietalkie.data.nearby

import android.content.Context
import com.example.walkietalkie.domain.model.ConnectionQuality
import com.example.walkietalkie.domain.model.NearbyPeer
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NearbyManager(
    private val context: Context,
    val localDeviceId: String = "local_device",
    val localDisplayName: String = "WalkieUser"
) {

    private val connectionsClient: ConnectionsClient by lazy {
        Nearby.getConnectionsClient(context)
    }

    private val serviceId = "com.example.walkietalkie.SERVICE_ID"

    private val _peers = MutableStateFlow<Map<String, NearbyPeer>>(emptyMap())
    val peers: StateFlow<Map<String, NearbyPeer>> = _peers.asStateFlow()

    private val _incomingPackets = MutableSharedFlow<ByteArray>(extraBufferCapacity = 64)
    val incomingPackets: SharedFlow<ByteArray> = _incomingPackets.asSharedFlow()

    val pendingConnectionRequests = MutableStateFlow<List<NearbyPeer>>(emptyList())

    private val payloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload) {
            payload.asBytes()?.let { bytes ->
                _incomingPackets.tryEmit(bytes)
            }
        }

        override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) {}
    }

    private val connectionLifecycleCallback = object : ConnectionLifecycleCallback() {
        override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
            connectionsClient.acceptConnection(endpointId, payloadCallback)
        }

        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            if (result.status.isSuccess) {
                _peers.update { currentPeers ->
                    val updated = NearbyPeer(
                        endpointId = endpointId,
                        displayName = "Peer $endpointId",
                        deviceId = endpointId,
                        isDirect = true,
                        hopCount = 0,
                        quality = ConnectionQuality.EXCELLENT,
                        trusted = true
                    )
                    currentPeers + (endpointId to updated)
                }
            }
        }

        override fun onDisconnected(endpointId: String) {
            _peers.update { currentPeers -> currentPeers - endpointId }
        }
    }

    private val endpointDiscoveryCallback = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
            val newPeer = NearbyPeer(
                endpointId = endpointId,
                displayName = info.endpointName,
                deviceId = endpointId,
                isDirect = false,
                hopCount = 1,
                quality = ConnectionQuality.GOOD,
                trusted = false
            )
            _peers.update { it + (endpointId to newPeer) }
        }

        override fun onEndpointLost(endpointId: String) {
            _peers.update { it - endpointId }
        }
    }

    fun startAdvertising() {
        val options = AdvertisingOptions.Builder().setStrategy(Strategy.P2P_CLUSTER).build()
        connectionsClient.startAdvertising(localDisplayName, serviceId, connectionLifecycleCallback, options)
    }

    fun startDiscovery() {
        val options = DiscoveryOptions.Builder().setStrategy(Strategy.P2P_CLUSTER).build()
        connectionsClient.startDiscovery(serviceId, endpointDiscoveryCallback, options)
    }

    fun stopAll() {
        connectionsClient.stopAdvertising()
        connectionsClient.stopDiscovery()
        connectionsClient.stopAllEndpoints()
        _peers.value = emptyMap()
    }

    fun send(endpointId: String, data: ByteArray) {
        connectionsClient.sendPayload(endpointId, Payload.fromBytes(data))
    }

    fun requestConnection(endpointId: String) {
        connectionsClient.requestConnection(localDisplayName, endpointId, connectionLifecycleCallback)
    }

    fun disconnect(endpointId: String) {
        connectionsClient.disconnectFromEndpoint(endpointId)
        _peers.update { it - endpointId }
    }

    fun acceptConnection(endpointId: String) {
        connectionsClient.acceptConnection(endpointId, payloadCallback)
    }

    fun rejectConnection(endpointId: String) {
        connectionsClient.rejectConnection(endpointId)
    }
}
