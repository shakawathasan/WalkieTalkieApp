package com.example.walkietalkie.data.local.dao

import androidx.room.*
import com.example.walkietalkie.data.local.entity.DeviceEntity
import com.example.walkietalkie.data.local.entity.MessageEntity
import com.example.walkietalkie.data.local.entity.UserEntity
import com.example.walkietalkie.data.local.entity.VibrationEventEntity
import com.example.walkietalkie.domain.model.MessageStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Upsert
    suspend fun upsert(user: UserEntity)

    @Query("SELECT * FROM users ORDER BY displayName")
    fun observeAll(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE userId = :id")
    suspend fun getById(id: String): UserEntity?

    @Query("UPDATE users SET trusted = :trusted WHERE userId = :id")
    suspend fun setTrusted(id: String, trusted: Boolean)

    @Query("UPDATE users SET blocked = :blocked WHERE userId = :id")
    suspend fun setBlocked(id: String, blocked: Boolean)
}

@Dao
interface DeviceDao {
    @Upsert
    suspend fun upsert(device: DeviceEntity)

    @Query("SELECT * FROM devices")
    fun observeAll(): Flow<List<DeviceEntity>>

    @Query("DELETE FROM devices WHERE deviceId = :id")
    suspend fun remove(id: String)
}

@Dao
interface MessageDao {
    @Upsert
    suspend fun upsert(message: MessageEntity)

    @Query("SELECT * FROM messages WHERE conversationId = :conversationId ORDER BY timestamp ASC")
    fun observeConversation(conversationId: String): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages WHERE status = :status")
    suspend fun getByStatus(status: MessageStatus): List<MessageEntity>

    @Query("SELECT * FROM messages WHERE messageId = :id LIMIT 1")
    suspend fun getById(id: String): MessageEntity?

    @Query("UPDATE messages SET status = :status WHERE messageId = :id")
    suspend fun updateStatus(id: String, status: MessageStatus)
}

@Dao
interface VibrationDao {
    @Upsert
    suspend fun upsert(event: VibrationEventEntity)

    @Query("SELECT * FROM vibration_events WHERE receiverId = :userId OR senderId = :userId ORDER BY timestamp DESC")
    fun observeForUser(userId: String): Flow<List<VibrationEventEntity>>

    @Query("UPDATE vibration_events SET acknowledged = 1 WHERE eventId = :id")
    suspend fun acknowledge(id: String)
}
