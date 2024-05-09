package org.team9432.dashboard.app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import org.team9432.dashboard.app.io.Client
import org.team9432.dashboard.app.ui.TabBar
import org.team9432.dashboard.app.ui.widgets.WidgetContainer
import org.team9432.dashboard.shared.Tab

// X and Y dimensions of the widget area
private var widgetAreaY by mutableFloatStateOf(0F)
private var widgetAreaX by mutableFloatStateOf(0F)

/** Displays the given widgets. */
@Composable
fun DisplayScreen(tabs: SnapshotStateMap<Int, Tab>) {
    var currentTab by remember { mutableIntStateOf(0) }
    Column {
        TabBar(tabs, currentTab, onTabChange = { currentTab = it })
        WidgetArea(tabs[currentTab] ?: Tab("", 0, 0, 0))
    }
}

@Composable
private fun WidgetArea(tab: Tab) {
    Surface(Modifier.fillMaxSize()) {
        Box(Modifier.fillMaxSize().onGloballyPositioned { widgetAreaY = it.size.height.toFloat(); widgetAreaX = it.size.width.toFloat() }) {
            val xPerUnit = widgetAreaX.pxToDp() / tab.numberOfCols
            val yPerUnit = widgetAreaY.pxToDp() / tab.numberOfRows

            Client.getWidgetsOnTab(tab.name).forEach {
                WidgetContainer(it.data.position, xPerUnit, yPerUnit) {
                    it.display()
                }
            }
        }
    }
}

/** Converts the value in px to dp. */
@Composable
fun Float.pxToDp() = (this / LocalDensity.current.density).dp