package org.team9432.dashboard.app.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.team9432.dashboard.app.Screen

@Composable
fun NavRail(currentScreen: Screen, onScreenChange: (Screen) -> Unit) {
    NavigationRail(containerColor = MaterialTheme.colorScheme.surfaceVariant) {
        NavigationRailItem(
            icon = { Icon(Icons.Default.Analytics, contentDescription = "View Data") },
            selected = currentScreen == Screen.DATA_VIEW,
            onClick = { onScreenChange(Screen.DATA_VIEW) },
            label = { Text(text = "Data View") }
        )
        NavigationRailItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            selected = currentScreen == Screen.SETTINGS,
            onClick = { onScreenChange(Screen.SETTINGS) },
            label = { Text(text = "Settings") }
        )
        Spacer(Modifier.weight(1F))

        NavigationRailItem(
            selected = false,
            enabled = false,
            onClick = {},
            icon = { Icon(if (AppState.connected) Icons.Default.Check else Icons.Default.Close, null) },
            colors = NavigationRailItemDefaults.colors(
                disabledIconColor = if (AppState.connected) Color.Green else Color.Red,
                disabledTextColor = NavigationRailItemDefaults.colors().unselectedTextColor
            ),
            label = { Text(if (AppState.connected) "Connected" else "Disconnected") }
        )
    }
}