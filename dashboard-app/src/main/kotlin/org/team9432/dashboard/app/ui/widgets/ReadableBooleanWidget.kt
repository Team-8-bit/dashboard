package org.team9432.dashboard.app.ui.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.team9432.dashboard.app.ui.widgets.util.SafeMutableState
import org.team9432.dashboard.shared.BooleanUpdate
import org.team9432.dashboard.shared.CreateWidget
import org.team9432.dashboard.shared.WidgetUpdate

/** Boolean displayed as a colored box. */
class ReadableBooleanWidget(data: CreateWidget): WidgetBase(data) {
    private var currentValue by SafeMutableState<Boolean?>(null)

    @Composable
    override fun display() {
        val value = currentValue ?: return

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = data.name, fontSize = 20.sp, textAlign = TextAlign.Center)
            Surface(
                Modifier.fillMaxWidth(0.6F).fillMaxHeight(0.3F).padding(top = 10.dp),
                color = if (value) Color.Green else Color.Red,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.onSurface)
            ) {}
        }
    }

    override fun acceptUpdate(update: WidgetUpdate) {
        if (update is BooleanUpdate) currentValue = update.value
    }
}