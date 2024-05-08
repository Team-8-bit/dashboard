package org.team9432.dashboard.app.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** Text widget. */
@Composable
fun WritableStringWidget(name: String, value: String, onValueChange: (String) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = name, fontSize = 20.sp, textAlign = TextAlign.Center)

        var wasFocused by remember { mutableStateOf(false) }
        val focusManager = LocalFocusManager.current

        var currentInput by remember { mutableStateOf<String?>(null) }

        OutlinedTextField(
            value = currentInput ?: value,
            onValueChange = { newValue ->
                currentInput = newValue
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(autoCorrect = false),
            modifier = Modifier.onKeyEvent {
                if (it.key == Key.Enter) focusManager.clearFocus()
                false
            }.onFocusChanged {
                if (!it.isFocused && wasFocused) { // When unfocused
                    currentInput?.let { onValueChange(it) }
                    currentInput = null
                }
                wasFocused = it.isFocused
            }.padding(10.dp)
        )
    }
}