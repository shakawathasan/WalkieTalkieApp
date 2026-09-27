package com.example.walkietalkie.domain.model;

/**
 * Delivery lifecycle of an outgoing message.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b\u00a8\u0006\t"}, d2 = {"Lcom/example/walkietalkie/domain/model/MessageStatus;", "", "(Ljava/lang/String;I)V", "PENDING", "SENDING", "SENT", "DELIVERED", "READ", "FAILED", "app_debug"})
public enum MessageStatus {
    /*public static final*/ PENDING /* = new PENDING() */,
    /*public static final*/ SENDING /* = new SENDING() */,
    /*public static final*/ SENT /* = new SENT() */,
    /*public static final*/ DELIVERED /* = new DELIVERED() */,
    /*public static final*/ READ /* = new READ() */,
    /*public static final*/ FAILED /* = new FAILED() */;
    
    MessageStatus() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.example.walkietalkie.domain.model.MessageStatus> getEntries() {
        return null;
    }
}