package com.example.walkietalkie.ui.nearby;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0015\u001a\u00020\u0016J\u000e\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\rJ\u0006\u0010\u0019\u001a\u00020\u0016J\u000e\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nRG\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r\u0018\u00010\f2\u0014\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r\u0018\u00010\f8F@BX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012\u00a8\u0006\u001b"}, d2 = {"Lcom/example/walkietalkie/ui/nearby/NearbyViewModel;", "Landroidx/lifecycle/ViewModel;", "app", "Lcom/example/walkietalkie/WalkieTalkieApp;", "(Lcom/example/walkietalkie/WalkieTalkieApp;)V", "peers", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/example/walkietalkie/domain/model/NearbyPeer;", "getPeers", "()Lkotlinx/coroutines/flow/StateFlow;", "<set-?>", "Lkotlin/Pair;", "", "pendingRequest", "getPendingRequest", "()Lkotlin/Pair;", "setPendingRequest", "(Lkotlin/Pair;)V", "pendingRequest$delegate", "Landroidx/compose/runtime/MutableState;", "accept", "", "connect", "endpointId", "decline", "disconnect", "app_debug"})
public final class NearbyViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.walkietalkie.WalkieTalkieApp app = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.walkietalkie.domain.model.NearbyPeer>> peers = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState pendingRequest$delegate = null;
    
    public NearbyViewModel(@org.jetbrains.annotations.NotNull()
    com.example.walkietalkie.WalkieTalkieApp app) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.walkietalkie.domain.model.NearbyPeer>> getPeers() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final kotlin.Pair<java.lang.String, java.lang.String> getPendingRequest() {
        return null;
    }
    
    private final void setPendingRequest(kotlin.Pair<java.lang.String, java.lang.String> p0) {
    }
    
    public final void connect(@org.jetbrains.annotations.NotNull()
    java.lang.String endpointId) {
    }
    
    public final void disconnect(@org.jetbrains.annotations.NotNull()
    java.lang.String endpointId) {
    }
    
    public final void accept() {
    }
    
    public final void decline() {
    }
}