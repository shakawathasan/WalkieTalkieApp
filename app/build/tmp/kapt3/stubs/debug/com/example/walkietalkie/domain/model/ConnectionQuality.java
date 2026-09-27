package com.example.walkietalkie.domain.model;

/**
 * Human-readable link quality bucket, derived from real transport signals — never invented.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/example/walkietalkie/domain/model/ConnectionQuality;", "", "(Ljava/lang/String;I)V", "STRONG", "MEDIUM", "WEAK", "LOST", "app_debug"})
public enum ConnectionQuality {
    /*public static final*/ STRONG /* = new STRONG() */,
    /*public static final*/ MEDIUM /* = new MEDIUM() */,
    /*public static final*/ WEAK /* = new WEAK() */,
    /*public static final*/ LOST /* = new LOST() */;
    
    ConnectionQuality() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.example.walkietalkie.domain.model.ConnectionQuality> getEntries() {
        return null;
    }
}