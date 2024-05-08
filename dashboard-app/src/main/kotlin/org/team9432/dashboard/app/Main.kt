package org.team9432.dashboard.app

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.team9432.dashboard.app.io.Client
import org.team9432.dashboard.app.io.Config
import org.team9432.dashboard.app.ui.AppState
import org.team9432.dashboard.app.ui.NavRail
import org.team9432.dashboard.app.ui.screens.DisplayScreen
import org.team9432.dashboard.app.ui.screens.SettingsScreen
import org.team9432.dashboard.app.ui.theme.AppTheme

enum class Screen {
    SETTINGS,
    DATA_VIEW
}

@Composable
@Preview
fun App() {
    AppTheme(darkTheme = AppState.isDarkMode) {
        Row {
            var screen by remember { mutableStateOf(Screen.DATA_VIEW) }

            NavRail(
                currentScreen = screen,
                onScreenChange = { screen = it }
            )
            when (screen) {
                Screen.SETTINGS -> SettingsScreen()
                Screen.DATA_VIEW -> DisplayScreen(Client.getCurrentTabs())
            }
        }
    }
}

fun main() {
    runBlocking {
        Config.refreshAppStateFromConfig()

        launch {
            Client.run()
        }

        application {
            Window(onCloseRequest = ::exitApplication, title = "Dashboard", state = WindowState(WindowPlacement.Maximized)) {
                App()
            }
        }
    }
}
