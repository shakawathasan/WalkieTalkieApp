package com.example.walkietalkie.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.walkietalkie.WalkieTalkieApp
import com.example.walkietalkie.ui.chats.ChatScreen
import com.example.walkietalkie.ui.nearby.NearbyScreen
import com.example.walkietalkie.ui.radar.RadarScreen
import com.example.walkietalkie.ui.settings.SettingsScreen
import com.example.walkietalkie.ui.walkie.WalkieScreen

private sealed class Dest(val route: String, val label: String) {
    object Walkie : Dest("walkie", "Walkie")
    object Chats : Dest("chats", "Chats")
    object Radar : Dest("radar", "Radar")
    object Nearby : Dest("nearby", "Nearby")
    object Settings : Dest("settings", "Settings")
}

private val destinations = listOf(Dest.Walkie, Dest.Chats, Dest.Radar, Dest.Nearby, Dest.Settings)

@Composable
fun WalkieTalkieNavHost(app: WalkieTalkieApp) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination
                destinations.forEach { dest ->
                    NavigationBarItem(
                        selected = currentRoute?.hierarchy?.any { it.route == dest.route } == true,
                        onClick = {
                            navController.navigate(dest.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { NavIcon(dest) },
                        label = { androidx.compose.material3.Text(dest.label) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Dest.Walkie.route,
            modifier = androidx.compose.ui.Modifier.padding(padding)
        ) {
            composable(Dest.Walkie.route) { WalkieScreen(app, activeContactId = "demo-contact") }
            composable(Dest.Chats.route) { ChatScreen(app, conversationId = "demo-conversation", receiverId = "demo-contact") }
            composable(Dest.Radar.route) { RadarScreen(app) }
            composable(Dest.Nearby.route) { NearbyScreen(app) }
            composable(Dest.Settings.route) { SettingsScreen() }
        }
    }
}

@Composable
private fun NavIcon(dest: Dest) {
    val icon = when (dest) {
        Dest.Walkie -> Icons.Filled.Mic
        Dest.Chats -> Icons.Filled.Chat
        Dest.Radar -> Icons.Filled.RadioButtonChecked
        Dest.Nearby -> Icons.Filled.NearMe
        Dest.Settings -> Icons.Filled.Settings
    }
    Icon(icon, contentDescription = dest.label)
}
