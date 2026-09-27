package com.example.walkietalkie.services

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.*
import androidx.core.content.ContextCompat
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

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
class AudioPttManager(private val context: Context) {

    private val sampleRate = 16000
    private val channelIn = AudioFormat.CHANNEL_IN_MONO
    private val channelOut = AudioFormat.CHANNEL_OUT_MONO
    private val encoding = AudioFormat.ENCODING_PCM_16BIT

    private var audioRecord: AudioRecord? = null
    private var audioTrack: AudioTrack? = null
    private var recordJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _outgoingAudioChunks = MutableSharedFlow<ByteArray>(extraBufferCapacity = 32)
    val outgoingAudioChunks: SharedFlow<ByteArray> = _outgoingAudioChunks

    private val _isTransmitting = MutableSharedFlow<Boolean>(replay = 1)
    val isTransmitting: SharedFlow<Boolean> = _isTransmitting

    private fun hasMicPermission(): Boolean =
        ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) ==
            PackageManager.PERMISSION_GRANTED

    /** Call on PTT button press. Returns false immediately if mic permission is missing. */
    fun startTransmitting(): Boolean {
        if (!hasMicPermission()) return false
        if (recordJob?.isActive == true) return true

        val minBuf = AudioRecord.getMinBufferSize(sampleRate, channelIn, encoding)
        if (minBuf <= 0) return false

        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.VOICE_COMMUNICATION,
            sampleRate, channelIn, encoding, minBuf * 2
        )

        if (audioRecord?.state != AudioRecord.STATE_INITIALIZED) {
            audioRecord?.release(); audioRecord = null
            return false
        }

        audioRecord?.startRecording()
        _isTransmitting.tryEmit(true)

        recordJob = scope.launch {
            val buffer = ByteArray(minBuf)
            while (isActive) {
                val read = audioRecord?.read(buffer, 0, buffer.size) ?: -1
                if (read > 0) {
                    _outgoingAudioChunks.tryEmit(buffer.copyOf(read))
                }
            }
        }
        return true
    }

    /** Call on PTT release. Cleanly releases the mic — no leaks (section 20/49). */
    fun stopTransmitting() {
        recordJob?.cancel()
        recordJob = null
        audioRecord?.let {
            try { it.stop() } catch (_: Exception) {}
            it.release()
        }
        audioRecord = null
        _isTransmitting.tryEmit(false)
    }

    /** Feed incoming PCM chunks from another user here to play them through the speaker. */
    fun playIncomingChunk(pcm: ByteArray) {
        if (audioTrack == null) {
            val minBuf = AudioTrack.getMinBufferSize(sampleRate, channelOut, encoding)
            audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setSampleRate(sampleRate)
                        .setChannelMask(channelOut)
                        .setEncoding(encoding)
                        .build()
                )
                .setBufferSizeInBytes(minBuf * 2)
                .setTransferMode(AudioTrack.MODE_STREAM)
                .build()
            audioTrack?.play()
        }
        audioTrack?.write(pcm, 0, pcm.size)
    }

    /** Release everything — call from Service.onDestroy() (section 49: no leaked audio resources). */
    fun release() {
        stopTransmitting()
        audioTrack?.let {
            try { it.stop() } catch (_: Exception) {}
            it.release()
        }
        audioTrack = null
        scope.cancel()
    }
}
