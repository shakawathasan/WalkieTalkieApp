package com.example.walkietalkie.data.remote;

/**
 * Real WebSocket client. Point WEBSOCKET_URL at your own backend
 * (plain WebSocket server, Firebase Realtime DB bridge, Supabase Realtime, etc).
 * This class only owns the socket lifecycle + reconnect; message shape/auth is up
 * to whatever server you wire it to — see the comment on connect().
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0005J\u0006\u0010\u001a\u001a\u00020\u001bJ\u0006\u0010\u001c\u001a\u00020\u001bJ\b\u0010\u001d\u001a\u00020\u001bH\u0002J\u000e\u0010\u001e\u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00020\bR\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\b0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/example/walkietalkie/data/remote/OnlineDataSource;", "", "webSocketUrl", "", "authToken", "(Ljava/lang/String;Ljava/lang/String;)V", "_incomingBytes", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/walkietalkie/data/remote/OnlineState;", "client", "Lokhttp3/OkHttpClient;", "incomingBytes", "Lkotlinx/coroutines/flow/SharedFlow;", "getIncomingBytes", "()Lkotlinx/coroutines/flow/SharedFlow;", "manuallyClosed", "", "socket", "Lokhttp3/WebSocket;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "connect", "", "disconnect", "scheduleReconnect", "send", "bytes", "app_debug"})
public final class OnlineDataSource {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String webSocketUrl = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String authToken = null;
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.OkHttpClient client = null;
    @org.jetbrains.annotations.Nullable()
    private okhttp3.WebSocket socket;
    private boolean manuallyClosed = false;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.walkietalkie.data.remote.OnlineState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.walkietalkie.data.remote.OnlineState> state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<byte[]> _incomingBytes = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<byte[]> incomingBytes = null;
    
    public OnlineDataSource(@org.jetbrains.annotations.NotNull()
    java.lang.String webSocketUrl, @org.jetbrains.annotations.Nullable()
    java.lang.String authToken) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.walkietalkie.data.remote.OnlineState> getState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<byte[]> getIncomingBytes() {
        return null;
    }
    
    public final void connect() {
    }
    
    private final void scheduleReconnect() {
    }
    
    public final boolean send(@org.jetbrains.annotations.NotNull()
    byte[] bytes) {
        return false;
    }
    
    public final void disconnect() {
    }
}