package com.example.walkietalkie

import android.app.Application
import com.example.walkietalkie.data.nearby.NearbyManager
import com.example.walkietalkie.domain.transport.CommunicationTransportManager

class WalkieTalkieApp : Application() {

    lateinit var nearbyManager: NearbyManager
        private set

    lateinit var transportManager: CommunicationTransportManager
        private set

    override fun onCreate() {
        super.onCreate()

        nearbyManager = NearbyManager(this)
        transportManager = CommunicationTransportManager(nearbyManager)
    }
}
