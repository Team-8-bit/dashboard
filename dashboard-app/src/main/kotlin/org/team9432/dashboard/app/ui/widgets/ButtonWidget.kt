package org.team9432.dashboard.app.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

/** Boolean displayed as a toggleable switch. */
@Composable
fun ButtonWidget(name: String, onClick: () -> Unit) {
    Box(contentAlignment = Alignment.Center) {
        Button(onClick = onClick) {
            Text(name)
        }
    }
}