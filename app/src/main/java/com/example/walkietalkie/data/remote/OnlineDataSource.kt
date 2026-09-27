package com.example.walkietalkie.data.remote

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit

enum class OnlineState { DISCONNECTED, CONNECTING, CONNECTED }

/**
 * Real WebSocket client. Point WEBSOCKET_URL at your own backend
 * (plain WebSocket server, Firebase Realtime DB bridge, Supabase Realtime, etc).
 * This class only owns the socket lifecycle + reconnect; message shape/auth is up
 * to whatever server you wire it to — see the comment on connect().
 */
class OnlineDataSource(
    private val webSocketUrl: String,
    private val authToken: String? = null
) {
    private val client = OkHttpClient.Builder()
        .pingInterval(20, TimeUnit.SECONDS)
        .build()

    private var socket: WebSocket? = null
    private var manuallyClosed = false

    private val _state = MutableStateFlow(OnlineState.DISCONNECTED)
    val state: StateFlow<OnlineState> = _state.asStateFlow()

    private val _incomingBytes = MutableSharedFlow<ByteArray>(extraBufferCapacity = 64)
    val incomingBytes: SharedFlow<ByteArray> = _incomingBytes

    fun connect() {
        manuallyClosed = false
        _state.value = OnlineState.CONNECTING
        val requestBuilder = Request.Builder().url(webSocketUrl)
        authToken?.let { requestBuilder.addHeader("Authorization", "Bearer $it") }

        socket = client.newWebSocket(requestBuilder.build(), object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                _state.value = OnlineState.CONNECTED
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                _incomingBytes.tryEmit(bytes.toByteArray())
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                _incomingBytes.tryEmit(text.toByteArray(Charsets.UTF_8))
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                _state.value = OnlineState.DISCONNECTED
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                _state.value = OnlineState.DISCONNECTED
                if (!manuallyClosed) scheduleReconnect()
            }
        })
    }

    private fun scheduleReconnect() {
        // Simple backoff; WorkManager can be layered on top for background retry (section 3/18).
        Thread {
            Thread.sleep(3000)
            if (!manuallyClosed) connect()
        }.start()
    }

    fun send(bytes: ByteArray): Boolean =
        socket?.send(ByteString.of(*bytes)) ?: false

    fun disconnect() {
        manuallyClosed = true
        socket?.close(1000, "client closing")
        _state.value = OnlineState.DISCONNECTED
    }
}
