package com.example.walkietalkie.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.walkietalkie.WalkieTalkieApp

/**
 * Keeps the transport layer (nearby + online) and PTT audio alive when the app
 * is backgrounded (section 37). Started/stopped from MainActivity/ViewModel —
 * it does not own business logic itself, it just keeps the process alive and
 * shows the required foreground notification.
 */
class WalkieForegroundService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NOTIFICATION_ID, buildNotification())
        // Ensure the shared transport manager is running even if MainActivity is gone.
        (application as WalkieTalkieApp).transportManager.start()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        (application as WalkieTalkieApp).transportManager.stop()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun buildNotification(): Notification {
        val channelId = "walkie_service"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(
                NotificationChannel(channelId, "Walkie Talkie", NotificationManager.IMPORTANCE_LOW)
            )
        }
        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Walkie Talkie active")
            .setContentText("Listening for nearby and online connections")
            .setSmallIcon(android.R.drawable.ic_btn_speak_now)
            .setOngoing(true)
            .build()
    }

    companion object {
        private const val NOTIFICATION_ID = 42
    }
}
