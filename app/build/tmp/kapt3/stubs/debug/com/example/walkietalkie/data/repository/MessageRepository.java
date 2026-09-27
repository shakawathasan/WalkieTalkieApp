package com.example.walkietalkie.data.repository;

/**
 * UI depends on this, never on Room or the transport layer directly (section 5).
 * Every send is written to Room first (offline-first) with status PENDING, then
 * handed to the transport manager — this is what makes "type -> chat works
 * regardless of connectivity" true rather than a UI illusion.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f2\u0006\u0010\u000f\u001a\u00020\tJ\u001a\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\r0\f2\u0006\u0010\u0012\u001a\u00020\tJ&\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010\u0017J\u001e\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\u001aH\u0086@\u00a2\u0006\u0002\u0010\u001bR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/example/walkietalkie/data/repository/MessageRepository;", "", "messageDao", "Lcom/example/walkietalkie/data/local/dao/MessageDao;", "vibrationDao", "Lcom/example/walkietalkie/data/local/dao/VibrationDao;", "transportManager", "Lcom/example/walkietalkie/domain/transport/CommunicationTransportManager;", "localUserId", "", "(Lcom/example/walkietalkie/data/local/dao/MessageDao;Lcom/example/walkietalkie/data/local/dao/VibrationDao;Lcom/example/walkietalkie/domain/transport/CommunicationTransportManager;Ljava/lang/String;)V", "observeConversation", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/example/walkietalkie/data/local/entity/MessageEntity;", "conversationId", "observeVibrations", "Lcom/example/walkietalkie/data/local/entity/VibrationEventEntity;", "userId", "sendText", "", "receiverId", "text", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendVibration", "pattern", "Lcom/example/walkietalkie/domain/model/VibrationPattern;", "(Ljava/lang/String;Lcom/example/walkietalkie/domain/model/VibrationPattern;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class MessageRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.example.walkietalkie.data.local.dao.MessageDao messageDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.walkietalkie.data.local.dao.VibrationDao vibrationDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.walkietalkie.domain.transport.CommunicationTransportManager transportManager = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String localUserId = null;
    
    public MessageRepository(@org.jetbrains.annotations.NotNull()
    com.example.walkietalkie.data.local.dao.MessageDao messageDao, @org.jetbrains.annotations.NotNull()
    com.example.walkietalkie.data.local.dao.VibrationDao vibrationDao, @org.jetbrains.annotations.NotNull()
    com.example.walkietalkie.domain.transport.CommunicationTransportManager transportManager, @org.jetbrains.annotations.NotNull()
    java.lang.String localUserId) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.example.walkietalkie.data.local.entity.MessageEntity>> observeConversation(@org.jetbrains.annotations.NotNull()
    java.lang.String conversationId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.example.walkietalkie.data.local.entity.VibrationEventEntity>> observeVibrations(@org.jetbrains.annotations.NotNull()
    java.lang.String userId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object sendText(@org.jetbrains.annotations.NotNull()
    java.lang.String conversationId, @org.jetbrains.annotations.NotNull()
    java.lang.String receiverId, @org.jetbrains.annotations.NotNull()
    java.lang.String text, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object sendVibration(@org.jetbrains.annotations.NotNull()
    java.lang.String receiverId, @org.jetbrains.annotations.NotNull()
    com.example.walkietalkie.domain.model.VibrationPattern pattern, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}