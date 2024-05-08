package org.team9432.dashboard.app.ui.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.team9432.dashboard.app.ui.screens.WidgetPositionInfo

/** The base of all displayed widgets, adds background and padding. */
@Composable
fun WidgetBase(position: WidgetPositionInfo, content: @Composable BoxScope.() -> Unit) {
    ElevatedCard(
        Modifier
            .width(position.xPerUnit * position.widgetPosition.colsSpanned)
            .height(position.yPerUnit * position.widgetPosition.rowsSpanned)
            .offset(x = position.xPerUnit * position.widgetPosition.col, y = position.yPerUnit * position.widgetPosition.row)
            .padding(5.dp)
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            content()
        }
    }
}