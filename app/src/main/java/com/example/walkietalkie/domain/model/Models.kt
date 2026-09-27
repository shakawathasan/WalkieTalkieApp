package com.example.walkietalkie.domain.model

/** How a message/packet is currently being carried. */
enum class TransportType {
    ONLINE,
    NEARBY_DIRECT,
    NEARBY_RELAY,
    PENDING
}

/** Delivery lifecycle of an outgoing message. */
enum class MessageStatus {
    PENDING,
    SENDING,
    SENT,
    DELIVERED,
    READ,
    FAILED
}

/** Human-readable link quality bucket, derived from real transport signals — never invented. */
enum class ConnectionQuality {
    STRONG,
    MEDIUM,
    WEAK,
    LOST
}

enum class VibrationPattern(val millis: LongArray) {
    SHORT(longArrayOf(0, 80)),
    DOUBLE(longArrayOf(0, 80, 80, 80)),
    TRIPLE(longArrayOf(0, 80, 80, 80, 80, 80)),
    LONG(longArrayOf(0, 400)),
    ATTENTION(longArrayOf(0, 80, 80, 80, 80, 400)),
    EMERGENCY(longArrayOf(0, 300, 100, 300, 100, 300, 100, 600))
}

enum class PacketType {
    TEXT,
    VIBRATION,
    VOICE_CHUNK,
    ACK,
    PRESENCE,
    CONTROL
}

/**
 * A single unit of data moving through the transport layer (direct or relayed).
 * Every field here exists so RelayManager can dedupe, expire, and hop-limit packets —
 * see section 12/36 of the spec.
 */
data class NetworkPacket(
    val messageId: String,
    val senderId: String,
    val destinationId: String,
    val timestamp: Long,
    val hopCount: Int,
    val maxHops: Int,
    val routeId: String,
    val packetType: PacketType,
    val payload: ByteArray
) {
    fun isExpired(ttlMillis: Long = 2 * 60_000L): Boolean =
        System.currentTimeMillis() - timestamp > ttlMillis

    fun canRelayFurther(): Boolean = hopCount < maxHops

    override fun equals(other: Any?): Boolean =
        other is NetworkPacket && other.messageId == messageId
    override fun hashCode(): Int = messageId.hashCode()
}

data class NearbyPeer(
    val endpointId: String,
    val deviceId: String,
    val displayName: String,
    val isDirect: Boolean,
    val hopCount: Int,
    val quality: ConnectionQuality,
    val trusted: Boolean
)
