package org.team9432.dashboard.app.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import org.team9432.dashboard.app.ui.widgets.util.SafeMutableState
import org.team9432.dashboard.shared.CreateWidget
import org.team9432.dashboard.shared.StringUpdate
import org.team9432.dashboard.shared.WidgetUpdate

class ReadableStringWidget(data: CreateWidget): WidgetBase(data) {
    private var currentValue by SafeMutableState<String?>(null)

    @Composable
    override fun display() {
        val value = currentValue ?: return

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = data.name, fontSize = 20.sp, textAlign = TextAlign.Center)
            Text(text = value, fontSize = 15.sp, fontStyle = FontStyle.Italic, textAlign = TextAlign.Center)
        }
    }

    override fun acceptUpdate(update: WidgetUpdate) {
        if (update is StringUpdate) currentValue = update.value
    }
}