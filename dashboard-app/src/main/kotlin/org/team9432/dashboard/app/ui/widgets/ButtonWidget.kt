package org.team9432.dashboard.app.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import org.team9432.dashboard.shared.ButtonUpdate
import org.team9432.dashboard.shared.CreateWidget
import org.team9432.dashboard.shared.WidgetUpdate

/** Boolean displayed as a toggleable switch. */
class ButtonWidget(data: CreateWidget): WidgetBase(data) {
    @Composable
    override fun display() {
        Box(contentAlignment = Alignment.Center) {
            Button(onClick = { sendUpdate(ButtonUpdate) }) {
                Text(data.name)
            }
        }
    }

    override fun acceptUpdate(update: WidgetUpdate) {}
}