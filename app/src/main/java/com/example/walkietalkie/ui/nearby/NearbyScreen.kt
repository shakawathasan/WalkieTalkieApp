package com.example.walkietalkie.ui.nearby

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.walkietalkie.data.nearby.NearbyManager

@Composable
fun NearbyScreen(
    nearbyManager: NearbyManager
) {
    val peersMap by nearbyManager.peers.collectAsState()
    val pendingRequests by nearbyManager.pendingConnectionRequests.collectAsState()
    val peers = peersMap.values.toList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
            .padding(16.dp)
    ) {
        Text(text = "Pending Requests", color = Color.White)
        LazyColumn {
            items(pendingRequests) { peer ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(peer.displayName, color = Color.White)
                    Row {
                        Button(onClick = { nearbyManager.acceptConnection(peer.endpointId) }) {
                            Text("Accept")
                        }
                        Button(onClick = { nearbyManager.rejectConnection(peer.endpointId) }) {
                            Text("Reject")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Discovered Peers", color = Color.White)
        LazyColumn {
            items(peers) { peer ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(peer.displayName, color = Color.White)
                    if (peer.isDirect) {
                        Button(onClick = { nearbyManager.disconnect(peer.endpointId) }) {
                            Text("Disconnect")
                        }
                    } else {
                        Button(onClick = { nearbyManager.requestConnection(peer.endpointId) }) {
                            Text("Connect")
                        }
                    }
                }
            }
        }
    }
}
