package com.example.walkietalkie.ui.walkie;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0011"}, d2 = {"Lcom/example/walkietalkie/ui/walkie/WalkieViewModel;", "Landroidx/lifecycle/ViewModel;", "app", "Lcom/example/walkietalkie/WalkieTalkieApp;", "(Lcom/example/walkietalkie/WalkieTalkieApp;)V", "currentRoute", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/example/walkietalkie/domain/model/TransportType;", "getCurrentRoute", "()Lkotlinx/coroutines/flow/StateFlow;", "sendVibration", "", "receiverId", "", "startTalking", "", "stopTalking", "app_debug"})
public final class WalkieViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.walkietalkie.WalkieTalkieApp app = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.walkietalkie.domain.model.TransportType> currentRoute = null;
    
    public WalkieViewModel(@org.jetbrains.annotations.NotNull()
    com.example.walkietalkie.WalkieTalkieApp app) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.walkietalkie.domain.model.TransportType> getCurrentRoute() {
        return null;
    }
    
    public final boolean startTalking() {
        return false;
    }
    
    public final void stopTalking() {
    }
    
    public final void sendVibration(@org.jetbrains.annotations.NotNull()
    java.lang.String receiverId) {
    }
}