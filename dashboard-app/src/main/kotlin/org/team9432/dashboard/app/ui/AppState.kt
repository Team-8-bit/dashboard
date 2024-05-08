package org.team9432.dashboard.app.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object AppState {

    /** Whether the dashboard is connected to the robot. */
    var connected by mutableStateOf(false)

    /** Whether the app is in dark mode. */
    var isDarkMode by mutableStateOf(true)
}