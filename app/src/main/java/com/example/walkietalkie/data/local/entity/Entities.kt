package com.example.walkietalkie.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.walkietalkie.domain.model.ConnectionQuality
import com.example.walkietalkie.domain.model.MessageStatus
import com.example.walkietalkie.domain.model.TransportType
import com.example.walkietalkie.domain.model.VibrationPattern

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val userId: String,
    val displayName: String,
    val trusted: Boolean = false,
    val blocked: Boolean = false,
    val lastSeen: Long = 0L
)

@Entity(tableName = "devices")
data class DeviceEntity(
    @PrimaryKey val deviceId: String,
    val userId: String,
    val connectionType: TransportType,
    val connectionQuality: ConnectionQuality,
    val hopCount: Int = 0,
    val lastSeen: Long = 0L,
    val trusted: Boolean = false
)

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val messageId: String,
    val conversationId: String,
    val senderId: String,
    val receiverId: String,
    val content: String,
    val timestamp: Long,
    val status: MessageStatus,
    val transport: TransportType,
    val route: String? = null
)

@Entity(tableName = "vibration_events")
data class VibrationEventEntity(
    @PrimaryKey val eventId: String,
    val senderId: String,
    val receiverId: String,
    val pattern: VibrationPattern,
    val timestamp: Long,
    val status: MessageStatus,
    val acknowledged: Boolean = false
)
