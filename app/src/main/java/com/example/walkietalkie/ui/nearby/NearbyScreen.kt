package com.example.walkietalkie.ui.nearby

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.walkietalkie.WalkieTalkieApp
import com.example.walkietalkie.domain.model.NearbyPeer
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NearbyViewModel(private val app: WalkieTalkieApp) : ViewModel() {
    val peers: StateFlow<List<NearbyPeer>> = app.nearbyManager.peers
        .map { it.values.toList() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            app.nearbyManager.pendingConnectionRequests.collect { (endpointId, name) ->
                pendingRequest = endpointId to name
            }
        }
    }

    var pendingRequest by mutableStateOf<Pair<String, String>?>(null)
        private set

    fun connect(endpointId: String) = app.nearbyManager.requestConnection(endpointId)
    fun disconnect(endpointId: String) = app.nearbyManager.disconnect(endpointId)
    fun accept() { pendingRequest?.let { app.nearbyManager.acceptConnection(it.first) }; pendingRequest = null }
    fun decline() { pendingRequest?.let { app.nearbyManager.rejectConnection(it.first) }; pendingRequest = null }
}

@Composable
fun NearbyScreen(app: WalkieTalkieApp) {
    val viewModel = remember { NearbyViewModel(app) }
    val peers by viewModel.peers.collectAsState()

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Nearby", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))

        viewModel.pendingRequest?.let { (_, name) ->
            Card(Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
                Column(Modifier.padding(12.dp)) {
                    Text("$name wants to connect")
                    Row {
                        Button(onClick = { viewModel.accept() }) { Text("Accept") }
                        Spacer(Modifier.width(8.dp))
                        OutlinedButton(onClick = { viewModel.decline() }) { Text("Decline") }
                    }
                }
            }
        }

        LazyColumn {
            items(peers) { peer ->
                Row(
                    Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(peer.displayName, style = MaterialTheme.typography.bodyLarge)
                        Text(
                            "${peer.quality.name.lowercase().replaceFirstChar { it.uppercase() }} · " +
                                if (peer.isDirect) "Direct" else "${peer.hopCount} hop(s)"
                        )
                    }
                    if (peer.isDirect) {
                        OutlinedButton(onClick = { viewModel.disconnect(peer.endpointId) }) { Text("Disconnect") }
                    } else {
                        Button(onClick = { viewModel.connect(peer.endpointId) }) { Text("Connect") }
                    }
                }
            }
        }
    }
}
