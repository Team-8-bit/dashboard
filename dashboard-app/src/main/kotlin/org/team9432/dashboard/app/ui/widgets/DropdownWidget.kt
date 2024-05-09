package org.team9432.dashboard.app.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.team9432.dashboard.app.ui.widgets.util.SafeMutableState
import org.team9432.dashboard.shared.CreateWidget
import org.team9432.dashboard.shared.DropdownSelected
import org.team9432.dashboard.shared.DropdownUpdate
import org.team9432.dashboard.shared.WidgetUpdate

class DropdownWidget(data: CreateWidget): WidgetBase(data) {
    private val options = mutableStateListOf<String>()
    private var currentOption by SafeMutableState<String?>(null)

    init {
        val update = data.initialUpdate as DropdownUpdate
        options.addAll(update.options)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun display() {
        val option = currentOption ?: return

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = data.name, fontSize = 20.sp, textAlign = TextAlign.Center)
            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth().menuAnchor().padding(10.dp),
                    readOnly = true,
                    value = option,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    singleLine = true,
                    onValueChange = {}
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    options.forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                expanded = false
                                sendUpdate(DropdownSelected(it))
                            }
                        )
                    }
                }
            }
        }
    }

    override fun acceptUpdate(update: WidgetUpdate) {
        when (update) {
            is DropdownUpdate -> {
                options.clear()
                options.addAll(update.options)

                currentOption = options.first()
            }

            is DropdownSelected -> {
                currentOption = update.option
            }

            else -> {}
        }
    }
}