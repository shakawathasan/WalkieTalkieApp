package com.example.walkietalkie.domain.transport

import com.example.walkietalkie.data.nearby.NearbyManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommunicationTransportManager(
    private val nearbyManager: NearbyManager,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {
    fun start() = startTransport()
    fun stop() = stopTransport()

    fun startTransport() {
        nearbyManager.startAdvertising()
        nearbyManager.startDiscovery()

        scope.launch {
            nearbyManager.incomingPackets.collect { packet ->
                // Handle packet
            }
        }
    }

    fun stopTransport() {
        nearbyManager.stopAll()
    }

    fun sendData(endpointId: String, data: ByteArray) {
        nearbyManager.send(endpointId, data)
    }

    fun sendPacket(endpointId: String, data: ByteArray) {
        sendData(endpointId, data)
    }
}
