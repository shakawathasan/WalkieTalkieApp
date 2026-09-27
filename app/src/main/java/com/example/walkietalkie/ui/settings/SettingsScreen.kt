package com.example.walkietalkie.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    var autoReconnect by remember { mutableStateOf(true) }
    var relayEnabled by remember { mutableStateOf(true) }
    var nearbyDiscovery by remember { mutableStateOf(true) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Settings", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        SettingSwitchRow("Auto reconnect", autoReconnect) { autoReconnect = it }
        SettingSwitchRow("Nearby discovery", nearbyDiscovery) { nearbyDiscovery = it }
        SettingSwitchRow("Relay networking", relayEnabled) { relayEnabled = it }

        Spacer(Modifier.height(24.dp))
        Text(
            "Wire these switches to your DataStore/SharedPreferences and read them in " +
                "WalkieTalkieApp before starting NearbyManager/CommunicationTransportManager.",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun SettingSwitchRow(label: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        Switch(checked = checked, onCheckedChange = onChange)
    }
}
