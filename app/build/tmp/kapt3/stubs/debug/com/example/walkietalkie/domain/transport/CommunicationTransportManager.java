package com.example.walkietalkie.domain.transport;

/**
 * Single source of truth for "how do we reach the outside world right now".
 * UI/ViewModels never touch NearbyManager or OnlineDataSource directly — they
 * go through here, matching the layering the spec calls for in section 5/6.
 *
 * Routing priority (section 6/43): Online -> Nearby Direct -> Nearby Relay -> Pending.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0002J\b\u0010!\u001a\u00020\"H\u0002J\b\u0010#\u001a\u00020\"H\u0002J\u0016\u0010$\u001a\u00020\"2\u0006\u0010\u001f\u001a\u00020 H\u0082@\u00a2\u0006\u0002\u0010%J\b\u0010&\u001a\u00020\"H\u0002J.\u0010\'\u001a\u00020\"2\u0006\u0010(\u001a\u00020\u00052\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u001e2\u0006\u0010,\u001a\u00020\u0005H\u0086@\u00a2\u0006\u0002\u0010-J\u0006\u0010.\u001a\u00020\"J\u0006\u0010/\u001a\u00020\"R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00110\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0018R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00060"}, d2 = {"Lcom/example/walkietalkie/domain/transport/CommunicationTransportManager;", "", "context", "Landroid/content/Context;", "localDeviceId", "", "nearbyManager", "Lcom/example/walkietalkie/data/nearby/NearbyManager;", "onlineDataSource", "Lcom/example/walkietalkie/data/remote/OnlineDataSource;", "messageDao", "Lcom/example/walkietalkie/data/local/dao/MessageDao;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "(Landroid/content/Context;Ljava/lang/String;Lcom/example/walkietalkie/data/nearby/NearbyManager;Lcom/example/walkietalkie/data/remote/OnlineDataSource;Lcom/example/walkietalkie/data/local/dao/MessageDao;Lkotlinx/coroutines/CoroutineScope;)V", "_internetAvailable", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "connectivityManager", "Landroid/net/ConnectivityManager;", "currentRoute", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/example/walkietalkie/domain/model/TransportType;", "getCurrentRoute", "()Lkotlinx/coroutines/flow/StateFlow;", "internetAvailable", "getInternetAvailable", "networkCallback", "Landroid/net/ConnectivityManager$NetworkCallback;", "encodeForOnline", "", "packet", "Lcom/example/walkietalkie/domain/model/NetworkPacket;", "observeIncoming", "", "observePendingQueueDrain", "onPacketReceived", "(Lcom/example/walkietalkie/domain/model/NetworkPacket;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "registerNetworkCallback", "sendPacket", "destinationId", "packetType", "Lcom/example/walkietalkie/domain/model/PacketType;", "payload", "messageId", "(Ljava/lang/String;Lcom/example/walkietalkie/domain/model/PacketType;[BLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "start", "stop", "app_debug"})
public final class CommunicationTransportManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String localDeviceId = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.walkietalkie.data.nearby.NearbyManager nearbyManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.walkietalkie.data.remote.OnlineDataSource onlineDataSource = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.walkietalkie.data.local.dao.MessageDao messageDao = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.NotNull()
    private final android.net.ConnectivityManager connectivityManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _internetAvailable = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> internetAvailable = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.walkietalkie.domain.model.TransportType> currentRoute = null;
    @org.jetbrains.annotations.NotNull()
    private final android.net.ConnectivityManager.NetworkCallback networkCallback = null;
    
    public CommunicationTransportManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String localDeviceId, @org.jetbrains.annotations.NotNull()
    com.example.walkietalkie.data.nearby.NearbyManager nearbyManager, @org.jetbrains.annotations.NotNull()
    com.example.walkietalkie.data.remote.OnlineDataSource onlineDataSource, @org.jetbrains.annotations.NotNull()
    com.example.walkietalkie.data.local.dao.MessageDao messageDao, @org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineScope scope) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getInternetAvailable() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.walkietalkie.domain.model.TransportType> getCurrentRoute() {
        return null;
    }
    
    public final void start() {
    }
    
    public final void stop() {
    }
    
    /**
     * Send text/vibration/voice. Always writes to Room first (offline-first, section 5/25).
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object sendPacket(@org.jetbrains.annotations.NotNull()
    java.lang.String destinationId, @org.jetbrains.annotations.NotNull()
    com.example.walkietalkie.domain.model.PacketType packetType, @org.jetbrains.annotations.NotNull()
    byte[] payload, @org.jetbrains.annotations.NotNull()
    java.lang.String messageId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final byte[] encodeForOnline(com.example.walkietalkie.domain.model.NetworkPacket packet) {
        return null;
    }
    
    private final void observeIncoming() {
    }
    
    private final java.lang.Object onPacketReceived(com.example.walkietalkie.domain.model.NetworkPacket packet, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Section 18/25: whenever a route becomes available, retry anything PENDING.
     */
    private final void observePendingQueueDrain() {
    }
    
    private final void registerNetworkCallback() {
    }
}