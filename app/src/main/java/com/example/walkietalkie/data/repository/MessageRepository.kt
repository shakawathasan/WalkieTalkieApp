package com.example.walkietalkie.data.repository

import com.example.walkietalkie.data.local.dao.MessageDao
import com.example.walkietalkie.data.local.dao.VibrationDao
import com.example.walkietalkie.data.local.entity.MessageEntity
import com.example.walkietalkie.data.local.entity.VibrationEventEntity
import com.example.walkietalkie.domain.model.*
import com.example.walkietalkie.domain.transport.CommunicationTransportManager
import kotlinx.coroutines.flow.Flow
import java.nio.charset.StandardCharsets
import java.util.UUID

/**
 * UI depends on this, never on Room or the transport layer directly (section 5).
 * Every send is written to Room first (offline-first) with status PENDING, then
 * handed to the transport manager — this is what makes "type -> chat works
 * regardless of connectivity" true rather than a UI illusion.
 */
class MessageRepository(
    private val messageDao: MessageDao,
    private val vibrationDao: VibrationDao,
    private val transportManager: CommunicationTransportManager,
    private val localUserId: String
) {
    fun observeConversation(conversationId: String): Flow<List<MessageEntity>> =
        messageDao.observeConversation(conversationId)

    fun observeVibrations(userId: String): Flow<List<VibrationEventEntity>> =
        vibrationDao.observeForUser(userId)

    suspend fun sendText(conversationId: String, receiverId: String, text: String) {
        val id = UUID.randomUUID().toString()
        messageDao.upsert(
            MessageEntity(
                messageId = id,
                conversationId = conversationId,
                senderId = localUserId,
                receiverId = receiverId,
                content = text,
                timestamp = System.currentTimeMillis(),
                status = MessageStatus.PENDING,
                transport = TransportType.PENDING
            )
        )
        transportManager.sendPacket(receiverId, PacketType.TEXT, text.toByteArray(StandardCharsets.UTF_8), id)
    }

    suspend fun sendVibration(receiverId: String, pattern: VibrationPattern) {
        val id = UUID.randomUUID().toString()
        vibrationDao.upsert(
            VibrationEventEntity(
                eventId = id,
                senderId = localUserId,
                receiverId = receiverId,
                pattern = pattern,
                timestamp = System.currentTimeMillis(),
                status = MessageStatus.PENDING
            )
        )
        transportManager.sendPacket(receiverId, PacketType.VIBRATION, pattern.name.toByteArray(StandardCharsets.UTF_8), id)
    }
}
