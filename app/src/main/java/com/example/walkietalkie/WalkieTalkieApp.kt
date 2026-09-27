package com.example.walkietalkie

import android.app.Application
import com.example.walkietalkie.data.local.AppDatabase
import com.example.walkietalkie.data.nearby.NearbyManager
import com.example.walkietalkie.data.remote.OnlineDataSource
import com.example.walkietalkie.data.repository.MessageRepository
import com.example.walkietalkie.domain.transport.CommunicationTransportManager
import com.example.walkietalkie.services.AudioPttManager
import com.example.walkietalkie.services.WalkieVibrationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.util.UUID

/**
 * Simple manual DI container. Swap for Hilt/Koin once the project grows —
 * kept explicit here so every wire-up is visible in one place.
 */
class WalkieTalkieApp : Application() {

    // Persisted across restarts once you back this with DataStore/SharedPreferences.
    val localUserId: String by lazy { "user-" + UUID.randomUUID().toString().take(8) }

    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    val database: AppDatabase by lazy { AppDatabase.getInstance(this) }

    val nearbyManager: NearbyManager by lazy {
        NearbyManager(this, localDeviceId = localUserId, localDisplayName = "Me")
    }

    // TODO: replace with your real backend's WebSocket URL before shipping.
    val onlineDataSource: OnlineDataSource by lazy {
        OnlineDataSource(webSocketUrl = "wss://example.invalid/walkie")
    }

    val transportManager: CommunicationTransportManager by lazy {
        CommunicationTransportManager(
            context = this,
            localDeviceId = localUserId,
            nearbyManager = nearbyManager,
            onlineDataSource = onlineDataSource,
            messageDao = database.messageDao(),
            scope = appScope
        )
    }

    val messageRepository: MessageRepository by lazy {
        MessageRepository(
            messageDao = database.messageDao(),
            vibrationDao = database.vibrationDao(),
            transportManager = transportManager,
            localUserId = localUserId
        )
    }

    val audioPttManager: AudioPttManager by lazy { AudioPttManager(this) }
    val vibrationManager: WalkieVibrationManager by lazy { WalkieVibrationManager(this) }
}
