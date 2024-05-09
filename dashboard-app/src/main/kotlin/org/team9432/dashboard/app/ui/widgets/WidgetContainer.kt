package org.team9432.dashboard.app.ui.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.team9432.dashboard.shared.WidgetPosition

/** The base of all displayed widgets, adds background and padding. */
@Composable
fun WidgetContainer(widgetPosition: WidgetPosition, xPerUnit: Dp, yPerUnit: Dp, content: @Composable BoxScope.() -> Unit) {
    ElevatedCard(
        Modifier
            .width(xPerUnit * widgetPosition.colsSpanned)
            .height(yPerUnit * widgetPosition.rowsSpanned)
            .offset(x = xPerUnit * widgetPosition.col, y = yPerUnit * widgetPosition.row)
            .padding(5.dp)
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            content()
        }
    }
}