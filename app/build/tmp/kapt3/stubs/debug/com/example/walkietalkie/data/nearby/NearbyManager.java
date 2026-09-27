package com.example.walkietalkie.data.nearby;

/**
 * Wraps Google's Nearby Connections API (P2P_STAR strategy) to give us:
 * - device discovery (advertise + discover)
 * - direct connections between two phones
 * - a basic relay: if we're connected to endpoints that aren't the final
 *   destination, we forward the packet on, decrementing hop budget.
 *
 * This is real networking (not simulated). What it does NOT do out of the box:
 * - true mesh topology tracking across many devices (kept intentionally simple —
 *   see RelayManager for where to extend routing decisions)
 * - end-to-end payload encryption (add before shipping — see section 13 of the spec)
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 B2\u00020\u0001:\u0001BB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0007J\u000e\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020\u0005J\u0012\u00100\u001a\u0004\u0018\u00010\n2\u0006\u00101\u001a\u000202H\u0002J\u000e\u00103\u001a\u00020.2\u0006\u0010/\u001a\u00020\u0005J\u0010\u00104\u001a\u0002022\u0006\u00105\u001a\u00020\nH\u0002J\u000e\u00106\u001a\u00020.2\u0006\u0010/\u001a\u00020\u0005J\u000e\u00107\u001a\u00020.2\u0006\u0010/\u001a\u00020\u0005J\u000e\u00108\u001a\u00020.2\u0006\u00105\u001a\u00020\nJ\u0006\u00109\u001a\u00020.J\u0006\u0010:\u001a\u00020.J\u0006\u0010;\u001a\u00020.J,\u0010<\u001a\u00020.2\u0006\u0010/\u001a\u00020\u00052\u0006\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020@2\n\b\u0002\u0010A\u001a\u0004\u0018\u00010\u0005H\u0002R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u000b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00100\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\n0\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004\u00a2\u0006\u0002\n\u0000R#\u0010!\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0\r0\"\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R#\u0010%\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00100\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001eR\u001a\u0010\'\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020(0\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010)\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020+0*X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u0005X\u0082D\u00a2\u0006\u0002\n\u0000\u00a8\u0006C"}, d2 = {"Lcom/example/walkietalkie/data/nearby/NearbyManager;", "", "context", "Landroid/content/Context;", "localDeviceId", "", "localDisplayName", "(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V", "_incomingPackets", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/example/walkietalkie/domain/model/NetworkPacket;", "_peers", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/example/walkietalkie/domain/model/NearbyPeer;", "_pendingConnectionRequests", "Lkotlin/Pair;", "client", "Lcom/google/android/gms/nearby/connection/ConnectionsClient;", "connectedEndpoints", "", "connectionLifecycleCallback", "Lcom/google/android/gms/nearby/connection/ConnectionLifecycleCallback;", "endpointDiscoveryCallback", "Lcom/google/android/gms/nearby/connection/EndpointDiscoveryCallback;", "endpointIdToDeviceId", "", "incomingPackets", "Lkotlinx/coroutines/flow/SharedFlow;", "getIncomingPackets", "()Lkotlinx/coroutines/flow/SharedFlow;", "payloadCallback", "Lcom/google/android/gms/nearby/connection/PayloadCallback;", "peers", "Lkotlinx/coroutines/flow/StateFlow;", "getPeers", "()Lkotlinx/coroutines/flow/StateFlow;", "pendingConnectionRequests", "getPendingConnectionRequests", "pendingConnections", "Lcom/google/android/gms/nearby/connection/ConnectionInfo;", "seenMessageIds", "Ljava/util/LinkedHashMap;", "", "serviceId", "acceptConnection", "", "endpointId", "decode", "bytes", "", "disconnect", "encode", "packet", "rejectConnection", "requestConnection", "send", "startAdvertising", "startDiscovery", "stopAll", "updatePeer", "isDirect", "", "quality", "Lcom/example/walkietalkie/domain/model/ConnectionQuality;", "name", "Companion", "app_debug"})
public final class NearbyManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String localDeviceId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String localDisplayName = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String serviceId = "com.example.walkietalkie.SERVICE";
    @org.jetbrains.annotations.NotNull()
    private final com.google.android.gms.nearby.connection.ConnectionsClient client = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Map<java.lang.String, com.example.walkietalkie.domain.model.NearbyPeer>> _peers = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, com.example.walkietalkie.domain.model.NearbyPeer>> peers = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.example.walkietalkie.domain.model.NetworkPacket> _incomingPackets = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<com.example.walkietalkie.domain.model.NetworkPacket> incomingPackets = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.LinkedHashMap<java.lang.String, java.lang.Long> seenMessageIds = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<java.lang.String> connectedEndpoints = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.String> endpointIdToDeviceId = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.android.gms.nearby.connection.ConnectionLifecycleCallback connectionLifecycleCallback = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, com.google.android.gms.nearby.connection.ConnectionInfo> pendingConnections = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<kotlin.Pair<java.lang.String, java.lang.String>> _pendingConnectionRequests = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<kotlin.Pair<java.lang.String, java.lang.String>> pendingConnectionRequests = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.android.gms.nearby.connection.EndpointDiscoveryCallback endpointDiscoveryCallback = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.android.gms.nearby.connection.PayloadCallback payloadCallback = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "NearbyManager";
    @org.jetbrains.annotations.NotNull()
    public static final com.example.walkietalkie.data.nearby.NearbyManager.Companion Companion = null;
    
    public NearbyManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String localDeviceId, @org.jetbrains.annotations.NotNull()
    java.lang.String localDisplayName) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.Map<java.lang.String, com.example.walkietalkie.domain.model.NearbyPeer>> getPeers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<com.example.walkietalkie.domain.model.NetworkPacket> getIncomingPackets() {
        return null;
    }
    
    public final void startAdvertising() {
    }
    
    public final void startDiscovery() {
    }
    
    public final void stopAll() {
    }
    
    public final void requestConnection(@org.jetbrains.annotations.NotNull()
    java.lang.String endpointId) {
    }
    
    public final void disconnect(@org.jetbrains.annotations.NotNull()
    java.lang.String endpointId) {
    }
    
    /**
     * Send a packet to a specific known destination. If we have no direct link to it,
     * flood it to all connected trusted endpoints so they can relay it onward.
     */
    public final void send(@org.jetbrains.annotations.NotNull()
    com.example.walkietalkie.domain.model.NetworkPacket packet) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<kotlin.Pair<java.lang.String, java.lang.String>> getPendingConnectionRequests() {
        return null;
    }
    
    public final void acceptConnection(@org.jetbrains.annotations.NotNull()
    java.lang.String endpointId) {
    }
    
    public final void rejectConnection(@org.jetbrains.annotations.NotNull()
    java.lang.String endpointId) {
    }
    
    private final void updatePeer(java.lang.String endpointId, boolean isDirect, com.example.walkietalkie.domain.model.ConnectionQuality quality, java.lang.String name) {
    }
    
    private final byte[] encode(com.example.walkietalkie.domain.model.NetworkPacket packet) {
        return null;
    }
    
    private final com.example.walkietalkie.domain.model.NetworkPacket decode(byte[] bytes) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/example/walkietalkie/data/nearby/NearbyManager$Companion;", "", "()V", "TAG", "", "newMessageId", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String newMessageId() {
            return null;
        }
    }
}