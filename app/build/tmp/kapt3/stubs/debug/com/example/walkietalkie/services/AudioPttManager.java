package com.example.walkietalkie.services;

/**
 * Real push-to-talk audio capture/playback using AudioRecord + AudioTrack.
 * Raw PCM is emitted in small chunks over [outgoingAudioChunks] so the transport
 * layer can ship it out (over Nearby payload streams or a WebSocket binary frame).
 *
 * NOTE ON CODEC: the spec asks for Opus for bandwidth/latency. Encoding to Opus
 * requires either Android's MediaCodec OPUS support (API 29+, encode-side support
 * varies by OEM) or bundling a native Opus library (e.g. via NDK). Wire that in
 * inside encodeChunk()/decodeChunk() below — this class is written so that's a
 * drop-in change and the rest of the app doesn't need to know the codec.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u001c\u001a\u00020\u0007H\u0002J\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\tJ\u0006\u0010 \u001a\u00020\u001eJ\u0006\u0010!\u001a\u00020\u0007J\u0006\u0010\"\u001a\u00020\u001eR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000fX\u0082D\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00070\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0014R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\t0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u000fX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Lcom/example/walkietalkie/services/AudioPttManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_isTransmitting", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "", "_outgoingAudioChunks", "", "audioRecord", "Landroid/media/AudioRecord;", "audioTrack", "Landroid/media/AudioTrack;", "channelIn", "", "channelOut", "encoding", "isTransmitting", "Lkotlinx/coroutines/flow/SharedFlow;", "()Lkotlinx/coroutines/flow/SharedFlow;", "outgoingAudioChunks", "getOutgoingAudioChunks", "recordJob", "Lkotlinx/coroutines/Job;", "sampleRate", "scope", "Lkotlinx/coroutines/CoroutineScope;", "hasMicPermission", "playIncomingChunk", "", "pcm", "release", "startTransmitting", "stopTransmitting", "app_debug"})
public final class AudioPttManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    private final int sampleRate = 16000;
    private final int channelIn = android.media.AudioFormat.CHANNEL_IN_MONO;
    private final int channelOut = android.media.AudioFormat.CHANNEL_OUT_MONO;
    private final int encoding = android.media.AudioFormat.ENCODING_PCM_16BIT;
    @org.jetbrains.annotations.Nullable()
    private android.media.AudioRecord audioRecord;
    @org.jetbrains.annotations.Nullable()
    private android.media.AudioTrack audioTrack;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job recordJob;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<byte[]> _outgoingAudioChunks = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<byte[]> outgoingAudioChunks = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<java.lang.Boolean> _isTransmitting = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<java.lang.Boolean> isTransmitting = null;
    
    public AudioPttManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<byte[]> getOutgoingAudioChunks() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<java.lang.Boolean> isTransmitting() {
        return null;
    }
    
    private final boolean hasMicPermission() {
        return false;
    }
    
    /**
     * Call on PTT button press. Returns false immediately if mic permission is missing.
     */
    public final boolean startTransmitting() {
        return false;
    }
    
    /**
     * Call on PTT release. Cleanly releases the mic — no leaks (section 20/49).
     */
    public final void stopTransmitting() {
    }
    
    /**
     * Feed incoming PCM chunks from another user here to play them through the speaker.
     */
    public final void playIncomingChunk(@org.jetbrains.annotations.NotNull()
    byte[] pcm) {
    }
    
    /**
     * Release everything — call from Service.onDestroy() (section 49: no leaked audio resources).
     */
    public final void release() {
    }
}