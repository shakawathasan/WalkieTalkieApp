package com.example.walkietalkie.data.local.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b2\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u001c\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\b0\r2\u0006\u0010\u000e\u001a\u00020\u0005H\'J\u001e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0014\u00a8\u0006\u0015"}, d2 = {"Lcom/example/walkietalkie/data/local/dao/MessageDao;", "", "getById", "Lcom/example/walkietalkie/data/local/entity/MessageEntity;", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getByStatus", "", "status", "Lcom/example/walkietalkie/domain/model/MessageStatus;", "(Lcom/example/walkietalkie/domain/model/MessageStatus;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeConversation", "Lkotlinx/coroutines/flow/Flow;", "conversationId", "updateStatus", "", "(Ljava/lang/String;Lcom/example/walkietalkie/domain/model/MessageStatus;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "upsert", "message", "(Lcom/example/walkietalkie/data/local/entity/MessageEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface MessageDao {
    
    @androidx.room.Upsert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object upsert(@org.jetbrains.annotations.NotNull()
    com.example.walkietalkie.data.local.entity.MessageEntity message, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM messages WHERE conversationId = :conversationId ORDER BY timestamp ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.walkietalkie.data.local.entity.MessageEntity>> observeConversation(@org.jetbrains.annotations.NotNull()
    java.lang.String conversationId);
    
    @androidx.room.Query(value = "SELECT * FROM messages WHERE status = :status")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getByStatus(@org.jetbrains.annotations.NotNull()
    com.example.walkietalkie.domain.model.MessageStatus status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.walkietalkie.data.local.entity.MessageEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM messages WHERE messageId = :id LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.walkietalkie.data.local.entity.MessageEntity> $completion);
    
    @androidx.room.Query(value = "UPDATE messages SET status = :status WHERE messageId = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    com.example.walkietalkie.domain.model.MessageStatus status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}