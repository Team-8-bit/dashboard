package org.team9432.dashboard.app.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import org.team9432.dashboard.app.io.Client
import org.team9432.dashboard.app.io.Config
import org.team9432.dashboard.app.ui.AppState

@Composable
fun SettingsScreen() {
    Surface(Modifier.fillMaxSize()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(Modifier.fillMaxWidth(0.9F).fillMaxHeight()) {
                BooleanConfig("Dark Mode", AppState.isDarkMode) { Config.setDarkMode(it) }
                StringConfig("Robot IP", Config.getRobotIP()) { Config.setRobotIP(it) }
                StringConfig("Robot Port", Config.getRobotPort(), numberOnly = true) { Config.setRobotPort(it) }
                ButtonConfig("Reconnect") { Client.reconnect() }
            }
        }
    }
}

@Composable
private fun ButtonConfig(title: String, onClick: () -> Unit) {
    ConfigBase(title) { Button(onClick = onClick) { Text(title) } }
}

@Composable
private fun StringConfig(title: String, currentValue: String, numberOnly: Boolean = false, onChange: (String) -> Unit) {
    var currentString by remember { mutableStateOf(currentValue) }
    var isError by remember { mutableStateOf(false) }
    var wasFocused by remember { mutableStateOf(false) }
    ConfigBase(title) {
        val focusManager = LocalFocusManager.current

        OutlinedTextField(
            currentString, { newValue ->
                isError = numberOnly && !newValue.all { it.isDigit() }
                currentString = newValue
            },
            singleLine = true,
            isError = isError,
            keyboardOptions = KeyboardOptions(autoCorrect = false),
            modifier = Modifier.onKeyEvent {
                if (it.key == Key.Enter) focusManager.clearFocus()
                false
            }.onFocusChanged {
                if (!it.isFocused && wasFocused) onChange(currentString)
                wasFocused = it.isFocused
            }
        )
    }
}

@Composable
private fun BooleanConfig(title: String, isActive: Boolean, onChange: (Boolean) -> Unit) {
    ConfigBase(title) { Switch(isActive, onChange) }
}

@Composable
private fun ConfigBase(title: String, content: @Composable () -> Unit) {
    ListItem(
        headlineContent = { Text(title) },
        trailingContent = content
    )
}