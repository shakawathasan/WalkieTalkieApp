package com.example.walkietalkie;

/**
 * Simple manual DI container. Swap for Hilt/Koin once the project grows —
 * kept explicit here so every wire-up is visible in one place.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\u000b\u001a\u00020\f8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000f\u0010\n\u001a\u0004\b\r\u0010\u000eR\u001b\u0010\u0010\u001a\u00020\u00118FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0014\u0010\n\u001a\u0004\b\u0012\u0010\u0013R\u001b\u0010\u0015\u001a\u00020\u00168FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0019\u0010\n\u001a\u0004\b\u0017\u0010\u0018R\u001b\u0010\u001a\u001a\u00020\u001b8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001e\u0010\n\u001a\u0004\b\u001c\u0010\u001dR\u001b\u0010\u001f\u001a\u00020 8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b#\u0010\n\u001a\u0004\b!\u0010\"R\u001b\u0010$\u001a\u00020%8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b(\u0010\n\u001a\u0004\b&\u0010\'R\u001b\u0010)\u001a\u00020*8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b-\u0010\n\u001a\u0004\b+\u0010,\u00a8\u0006."}, d2 = {"Lcom/example/walkietalkie/WalkieTalkieApp;", "Landroid/app/Application;", "()V", "appScope", "Lkotlinx/coroutines/CoroutineScope;", "audioPttManager", "Lcom/example/walkietalkie/services/AudioPttManager;", "getAudioPttManager", "()Lcom/example/walkietalkie/services/AudioPttManager;", "audioPttManager$delegate", "Lkotlin/Lazy;", "database", "Lcom/example/walkietalkie/data/local/AppDatabase;", "getDatabase", "()Lcom/example/walkietalkie/data/local/AppDatabase;", "database$delegate", "localUserId", "", "getLocalUserId", "()Ljava/lang/String;", "localUserId$delegate", "messageRepository", "Lcom/example/walkietalkie/data/repository/MessageRepository;", "getMessageRepository", "()Lcom/example/walkietalkie/data/repository/MessageRepository;", "messageRepository$delegate", "nearbyManager", "Lcom/example/walkietalkie/data/nearby/NearbyManager;", "getNearbyManager", "()Lcom/example/walkietalkie/data/nearby/NearbyManager;", "nearbyManager$delegate", "onlineDataSource", "Lcom/example/walkietalkie/data/remote/OnlineDataSource;", "getOnlineDataSource", "()Lcom/example/walkietalkie/data/remote/OnlineDataSource;", "onlineDataSource$delegate", "transportManager", "Lcom/example/walkietalkie/domain/transport/CommunicationTransportManager;", "getTransportManager", "()Lcom/example/walkietalkie/domain/transport/CommunicationTransportManager;", "transportManager$delegate", "vibrationManager", "Lcom/example/walkietalkie/services/WalkieVibrationManager;", "getVibrationManager", "()Lcom/example/walkietalkie/services/WalkieVibrationManager;", "vibrationManager$delegate", "app_debug"})
public final class WalkieTalkieApp extends android.app.Application {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy localUserId$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope appScope = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy database$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy nearbyManager$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy onlineDataSource$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy transportManager$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy messageRepository$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy audioPttManager$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy vibrationManager$delegate = null;
    
    public WalkieTalkieApp() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLocalUserId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.walkietalkie.data.local.AppDatabase getDatabase() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.walkietalkie.data.nearby.NearbyManager getNearbyManager() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.walkietalkie.data.remote.OnlineDataSource getOnlineDataSource() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.walkietalkie.domain.transport.CommunicationTransportManager getTransportManager() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.walkietalkie.data.repository.MessageRepository getMessageRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.walkietalkie.services.AudioPttManager getAudioPttManager() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.walkietalkie.services.WalkieVibrationManager getVibrationManager() {
        return null;
    }
}