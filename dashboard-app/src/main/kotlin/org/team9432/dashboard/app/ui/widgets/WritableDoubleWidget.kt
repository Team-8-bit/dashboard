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
import org.team9432.dashboard.app.ui.widgets.util.SafeMutableState
import org.team9432.dashboard.shared.CreateWidget
import org.team9432.dashboard.shared.DoubleUpdate
import org.team9432.dashboard.shared.WidgetUpdate

class WritableDoubleWidget(data: CreateWidget): WidgetBase(data) {
    private var currentValue by SafeMutableState<Double?>(null)

    @Composable
    override fun display() {
        val value = currentValue ?: return

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = data.name, fontSize = 20.sp, textAlign = TextAlign.Center)

            var wasFocused by remember { mutableStateOf(false) }
            val focusManager = LocalFocusManager.current

            var currentInput by remember { mutableStateOf<String?>(null) }

            var isError by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = currentInput ?: value.toString(),
                onValueChange = { newValue ->
                    isError = newValue.toDoubleOrNull() == null

                    currentInput = newValue
                },
                singleLine = true,
                isError = isError,
                keyboardOptions = KeyboardOptions(autoCorrect = false),
                modifier = Modifier.onKeyEvent {
                    if (it.key == Key.Enter) focusManager.clearFocus()
                    false
                }.onFocusChanged {
                    if (!it.isFocused && wasFocused) { // When unfocused
                        currentInput?.toDoubleOrNull()?.let { sendUpdate(DoubleUpdate(it)) }
                        isError = false
                        currentInput = null
                    }
                    wasFocused = it.isFocused
                }.padding(10.dp)
            )
        }
    }

    override fun acceptUpdate(update: WidgetUpdate) {
        if (update is DoubleUpdate) currentValue = update.value
    }
}