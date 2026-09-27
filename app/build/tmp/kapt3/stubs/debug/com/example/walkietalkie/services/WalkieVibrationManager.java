package com.example.walkietalkie.services;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/example/walkietalkie/services/WalkieVibrationManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "vibrator", "Landroid/os/Vibrator;", "cancel", "", "play", "pattern", "Lcom/example/walkietalkie/domain/model/VibrationPattern;", "app_debug"})
public final class WalkieVibrationManager {
    @org.jetbrains.annotations.NotNull()
    private final android.os.Vibrator vibrator = null;
    
    public WalkieVibrationManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final void play(@org.jetbrains.annotations.NotNull()
    com.example.walkietalkie.domain.model.VibrationPattern pattern) {
    }
    
    public final void cancel() {
    }
}