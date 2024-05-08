package org.team9432.dashboard.app.ui.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

/** Boolean displayed as a toggleable switch. */
@Composable
fun WritableBooleanWidget(name: String, value: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = name, fontSize = 20.sp, textAlign = TextAlign.Center)
        Switch(checked = value, onCheckedChange = onCheckedChange)
    }
}