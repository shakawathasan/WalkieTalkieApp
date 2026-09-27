package com.example.walkietalkie.ui.walkie

import androidx.compose.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.walkietalkie.WalkieTalkieApp
import com.example.walkietalkie.domain.model.TransportType
import com.example.walkietalkie.domain.model.VibrationPattern
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WalkieViewModel(private val app: WalkieTalkieApp) : ViewModel() {
    val currentRoute: StateFlow<TransportType> = app.transportManager.currentRoute

    fun startTalking(): Boolean = app.audioPttManager.startTransmitting()
    fun stopTalking() = app.audioPttManager.stopTransmitting()

    fun sendVibration(receiverId: String) {
        viewModelScope.launch { app.messageRepository.sendVibration(receiverId, VibrationPattern.SHORT) }
    }
}

@Composable
fun WalkieScreen(app: WalkieTalkieApp, activeContactId: String) {
    val viewModel = remember { WalkieViewModel(app) }
    val route by viewModel.currentRoute.collectAsState()
    var isTransmitting by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("TEAM ALPHA", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            AssistChip(onClick = {}, label = { Text(routeLabel(route)) })
        }

        Box(
            modifier = Modifier
                .size(220.dp)
                .clip(CircleShape)
                .background(if (isTransmitting) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            val started = viewModel.startTalking()
                            isTransmitting = started
                            tryAwaitRelease()
                            viewModel.stopTalking()
                            isTransmitting = false
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                if (isTransmitting) "TRANSMITTING" else "HOLD TO TALK",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Button(onClick = { viewModel.sendVibration(activeContactId) }) {
            Text("VIBRATE")
        }
    }
}

private fun routeLabel(route: TransportType): String = when (route) {
    TransportType.ONLINE -> "Online"
    TransportType.NEARBY_DIRECT -> "Nearby · Direct"
    TransportType.NEARBY_RELAY -> "Nearby · Relay"
    TransportType.PENDING -> "Offline · Waiting for connection"
}
