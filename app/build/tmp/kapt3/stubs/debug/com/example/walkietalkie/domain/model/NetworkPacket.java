package com.example.walkietalkie.domain.model;

/**
 * A single unit of data moving through the transport layer (direct or relayed).
 * Every field here exists so RelayManager can dedupe, expire, and hop-limit packets —
 * see section 12/36 of the spec.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0011\b\u0086\b\u0018\u00002\u00020\u0001BM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\u0002\u0010\u0010J\u0006\u0010\u001f\u001a\u00020 J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0003H\u00c6\u0003J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\u0007H\u00c6\u0003J\t\u0010%\u001a\u00020\tH\u00c6\u0003J\t\u0010&\u001a\u00020\tH\u00c6\u0003J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\rH\u00c6\u0003J\t\u0010)\u001a\u00020\u000fH\u00c6\u0003Jc\u0010*\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u00c6\u0001J\u0013\u0010+\u001a\u00020 2\b\u0010,\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010-\u001a\u00020\tH\u0016J\u0010\u0010.\u001a\u00020 2\b\b\u0002\u0010/\u001a\u00020\u0007J\t\u00100\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\n\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0012R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001e\u00a8\u00061"}, d2 = {"Lcom/example/walkietalkie/domain/model/NetworkPacket;", "", "messageId", "", "senderId", "destinationId", "timestamp", "", "hopCount", "", "maxHops", "routeId", "packetType", "Lcom/example/walkietalkie/domain/model/PacketType;", "payload", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JIILjava/lang/String;Lcom/example/walkietalkie/domain/model/PacketType;[B)V", "getDestinationId", "()Ljava/lang/String;", "getHopCount", "()I", "getMaxHops", "getMessageId", "getPacketType", "()Lcom/example/walkietalkie/domain/model/PacketType;", "getPayload", "()[B", "getRouteId", "getSenderId", "getTimestamp", "()J", "canRelayFurther", "", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "isExpired", "ttlMillis", "toString", "app_debug"})
public final class NetworkPacket {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String messageId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String senderId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String destinationId = null;
    private final long timestamp = 0L;
    private final int hopCount = 0;
    private final int maxHops = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String routeId = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.walkietalkie.domain.model.PacketType packetType = null;
    @org.jetbrains.annotations.NotNull()
    private final byte[] payload = null;
    
    public NetworkPacket(@org.jetbrains.annotations.NotNull()
    java.lang.String messageId, @org.jetbrains.annotations.NotNull()
    java.lang.String senderId, @org.jetbrains.annotations.NotNull()
    java.lang.String destinationId, long timestamp, int hopCount, int maxHops, @org.jetbrains.annotations.NotNull()
    java.lang.String routeId, @org.jetbrains.annotations.NotNull()
    com.example.walkietalkie.domain.model.PacketType packetType, @org.jetbrains.annotations.NotNull()
    byte[] payload) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMessageId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSenderId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDestinationId() {
        return null;
    }
    
    public final long getTimestamp() {
        return 0L;
    }
    
    public final int getHopCount() {
        return 0;
    }
    
    public final int getMaxHops() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRouteId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.walkietalkie.domain.model.PacketType getPacketType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final byte[] getPayload() {
        return null;
    }
    
    public final boolean isExpired(long ttlMillis) {
        return false;
    }
    
    public final boolean canRelayFurther() {
        return false;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    public final long component4() {
        return 0L;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int component6() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.walkietalkie.domain.model.PacketType component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final byte[] component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.walkietalkie.domain.model.NetworkPacket copy(@org.jetbrains.annotations.NotNull()
    java.lang.String messageId, @org.jetbrains.annotations.NotNull()
    java.lang.String senderId, @org.jetbrains.annotations.NotNull()
    java.lang.String destinationId, long timestamp, int hopCount, int maxHops, @org.jetbrains.annotations.NotNull()
    java.lang.String routeId, @org.jetbrains.annotations.NotNull()
    com.example.walkietalkie.domain.model.PacketType packetType, @org.jetbrains.annotations.NotNull()
    byte[] payload) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}