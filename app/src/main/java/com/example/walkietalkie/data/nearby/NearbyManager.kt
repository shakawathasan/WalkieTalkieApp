package com.example.walkietalkie.data.nearby

import android.content.Context
import com.example.walkietalkie.domain.model.NearbyPeer
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NearbyManager(private val context: Context) {

    private val connectionsClient: ConnectionsClient by lazy {
        Nearby.getConnectionsClient(context)
    }

    private val serviceId = "com.example.walkietalkie.SERVICE_ID"

    private val _peers = MutableStateFlow<Map<String, NearbyPeer>>(emptyMap())
    val peers: StateFlow<Map<String, NearbyPeer>> = _peers.asStateFlow()

    private val payloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload) {
            payload.asBytes()?.let { bytes ->
                // Process incoming bytes / audio / mesh packets here
            }
        }

        override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) {
            // Track transfer status if needed
        }
    }

    private val connectionLifecycleCallback = object : ConnectionLifecycleCallback() {
        override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
            // Automatically accept connection for Walkie-Talkie auto-mesh
            connectionsClient.acceptConnection(endpointId, payloadCallback)
        }

        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            if (result.status.isSuccess) {
                _peers.update { currentPeers ->
                    val existing = currentPeers[endpointId]
                    val updated = existing?.copy(isDirect = true) ?: NearbyPeer(
                        endpointId = endpointId,
                        displayName = "Peer $endpointId",
                        isDirect = true,
                        hopCount = 0
                    )
                    currentPeers + (endpointId to updated)
                }
            }
        }

        override fun onDisconnected(endpointId: String) {
            _peers.update { currentPeers ->
                currentPeers - endpointId
            }
        }
    }

    private val endpointDiscoveryCallback = object : EndpointDiscoveryCallback() {
        override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
            _peers.update { currentPeers ->
                val newPeer = NearbyPeer(
                    endpointId = endpointId,
                    displayName = info.endpointName,
                    isDirect = false,
                    hopCount = 1
                )
                currentPeers + (endpointId to newPeer)
            }

            // Initiate connection automatically
            connectionsClient.requestConnection(
                "WalkieUser",
                endpointId,
                connectionLifecycleCallback
            )
        }

        override fun onEndpointLost(endpointId: String) {
            _peers.update { currentPeers ->
                currentPeers - endpointId
            }
        }
    }

    fun startAdvertisingAndDiscovery(userName: String = "WalkieUser") {
        val advertisingOptions = AdvertisingOptions.Builder()
            .setStrategy(Strategy.P2P_CLUSTER)
            .build()

        connectionsClient.startAdvertising(
            userName,
            serviceId,
            connectionLifecycleCallback,
            advertisingOptions
        )

        val discoveryOptions = DiscoveryOptions.Builder()
            .setStrategy(Strategy.P2P_CLUSTER)
            .build()

        connectionsClient.startDiscovery(
            serviceId,
            endpointDiscoveryCallback,
            discoveryOptions
        )
    }

    fun stopAdvertisingAndDiscovery() {
        connectionsClient.stopAdvertising()
        connectionsClient.stopDiscovery()
        connectionsClient.stopAllEndpoints()
        _peers.value = emptyMap()
    }

    fun sendPayload(endpointId: String, data: ByteArray) {
        connectionsClient.sendPayload(endpointId, Payload.fromBytes(data))
    }

    fun broadcastPayload(data: ByteArray) {
        val directEndpoints = _peers.value.filterValues { it.isDirect }.keys.toList()
        if (directEndpoints.isNotEmpty()) {
            connectionsClient.sendPayload(directEndpoints, Payload.fromBytes(data))
        }
    }
}
