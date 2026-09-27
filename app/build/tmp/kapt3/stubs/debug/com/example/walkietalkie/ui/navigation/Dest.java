package com.example.walkietalkie.ui.navigation;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u00002\u00020\u0001:\u0005\t\n\u000b\f\rB\u0017\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u0082\u0001\u0005\u000e\u000f\u0010\u0011\u0012\u00a8\u0006\u0013"}, d2 = {"Lcom/example/walkietalkie/ui/navigation/Dest;", "", "route", "", "label", "(Ljava/lang/String;Ljava/lang/String;)V", "getLabel", "()Ljava/lang/String;", "getRoute", "Chats", "Nearby", "Radar", "Settings", "Walkie", "Lcom/example/walkietalkie/ui/navigation/Dest$Chats;", "Lcom/example/walkietalkie/ui/navigation/Dest$Nearby;", "Lcom/example/walkietalkie/ui/navigation/Dest$Radar;", "Lcom/example/walkietalkie/ui/navigation/Dest$Settings;", "Lcom/example/walkietalkie/ui/navigation/Dest$Walkie;", "app_debug"})
abstract class Dest {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String route = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String label = null;
    
    private Dest(java.lang.String route, java.lang.String label) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRoute() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLabel() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/walkietalkie/ui/navigation/Dest$Chats;", "Lcom/example/walkietalkie/ui/navigation/Dest;", "()V", "app_debug"})
    public static final class Chats extends com.example.walkietalkie.ui.navigation.Dest {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.walkietalkie.ui.navigation.Dest.Chats INSTANCE = null;
        
        private Chats() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/walkietalkie/ui/navigation/Dest$Nearby;", "Lcom/example/walkietalkie/ui/navigation/Dest;", "()V", "app_debug"})
    public static final class Nearby extends com.example.walkietalkie.ui.navigation.Dest {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.walkietalkie.ui.navigation.Dest.Nearby INSTANCE = null;
        
        private Nearby() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/walkietalkie/ui/navigation/Dest$Radar;", "Lcom/example/walkietalkie/ui/navigation/Dest;", "()V", "app_debug"})
    public static final class Radar extends com.example.walkietalkie.ui.navigation.Dest {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.walkietalkie.ui.navigation.Dest.Radar INSTANCE = null;
        
        private Radar() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/walkietalkie/ui/navigation/Dest$Settings;", "Lcom/example/walkietalkie/ui/navigation/Dest;", "()V", "app_debug"})
    public static final class Settings extends com.example.walkietalkie.ui.navigation.Dest {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.walkietalkie.ui.navigation.Dest.Settings INSTANCE = null;
        
        private Settings() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/walkietalkie/ui/navigation/Dest$Walkie;", "Lcom/example/walkietalkie/ui/navigation/Dest;", "()V", "app_debug"})
    public static final class Walkie extends com.example.walkietalkie.ui.navigation.Dest {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.walkietalkie.ui.navigation.Dest.Walkie INSTANCE = null;
        
        private Walkie() {
        }
    }
}