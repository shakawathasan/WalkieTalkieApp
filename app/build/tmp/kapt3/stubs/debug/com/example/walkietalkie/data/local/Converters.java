package com.example.walkietalkie.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\bH\u0007J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\nH\u0007J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\fH\u0007J\u0010\u0010\r\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u000e\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u0004H\u0007J\u0010\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u0004H\u0007\u00a8\u0006\u0011"}, d2 = {"Lcom/example/walkietalkie/data/local/Converters;", "", "()V", "fromPattern", "", "v", "Lcom/example/walkietalkie/domain/model/VibrationPattern;", "fromQuality", "Lcom/example/walkietalkie/domain/model/ConnectionQuality;", "fromStatus", "Lcom/example/walkietalkie/domain/model/MessageStatus;", "fromTransport", "Lcom/example/walkietalkie/domain/model/TransportType;", "toPattern", "toQuality", "toStatus", "toTransport", "app_debug"})
public final class Converters {
    
    public Converters() {
        super();
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromStatus(@org.jetbrains.annotations.NotNull()
    com.example.walkietalkie.domain.model.MessageStatus v) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final com.example.walkietalkie.domain.model.MessageStatus toStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromTransport(@org.jetbrains.annotations.NotNull()
    com.example.walkietalkie.domain.model.TransportType v) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final com.example.walkietalkie.domain.model.TransportType toTransport(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromQuality(@org.jetbrains.annotations.NotNull()
    com.example.walkietalkie.domain.model.ConnectionQuality v) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final com.example.walkietalkie.domain.model.ConnectionQuality toQuality(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String fromPattern(@org.jetbrains.annotations.NotNull()
    com.example.walkietalkie.domain.model.VibrationPattern v) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.NotNull()
    public final com.example.walkietalkie.domain.model.VibrationPattern toPattern(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
        return null;
    }
}