package org.team9432.dashboard.app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.team9432.dashboard.app.io.Client
import org.team9432.dashboard.app.ui.TabBar
import org.team9432.dashboard.app.ui.widgets.*
import org.team9432.dashboard.shared.Tab
import org.team9432.dashboard.shared.WidgetDefinition
import org.team9432.dashboard.shared.WidgetPosition
import org.team9432.dashboard.shared.WidgetType
import org.team9432.dashboard.shared.WidgetType.*

// X and Y dimensions of the widget area
private var widgetAreaY by mutableFloatStateOf(0F)
private var widgetAreaX by mutableFloatStateOf(0F)

data class WidgetPositionInfo(val widgetPosition: WidgetPosition, val xPerUnit: Dp, val yPerUnit: Dp)

/** Displays the given widgets. */
@Composable
fun DisplayScreen(tabs: List<Tab>) {
    var currentTab by remember { mutableIntStateOf(0) }
    Column {
        TabBar(tabs, currentTab, onTabChange = { currentTab = it })
        WidgetArea(tabs.firstOrNull { it.index == currentTab } ?: Tab("", 0, 0, 0, emptyList()))
    }
}

@Composable
private fun WidgetArea(tab: Tab) {
    Surface(Modifier.fillMaxSize()) {
        Box(Modifier.fillMaxSize().onGloballyPositioned { widgetAreaY = it.size.height.toFloat(); widgetAreaX = it.size.width.toFloat() }) {
            val xPerUnit = widgetAreaX.pxToDp() / tab.numberOfCols
            val yPerUnit = widgetAreaY.pxToDp() / tab.numberOfRows

            tab.widgets.forEach { (widget, position) -> Widget(widget, Client.getWidgetData(widget.id), WidgetPositionInfo(position, xPerUnit, yPerUnit)) }
        }
    }
}

/** Displays a widget of a given size at the given position. */
@Composable
fun Widget(widget: WidgetDefinition, state: MutableState<String>?, position: WidgetPositionInfo) {
    WidgetBase(position) {
        if (state != null) {
            display(widget.name, state.value, widget.type) { Client.updateWidget(widget.id, it) }
        } else {
            TextWidget(widget.name, "missing value")
        }
    }
}

/** Finds and displays the correct type of widget by the given name. */
@Composable
fun display(name: String, value: String, widgetType: WidgetType, sendUpdate: (String) -> Unit) {
    when (widgetType) {
        DisplayOnlyString -> TextWidget(name, value)
        DisplayOnlyBoolean -> ImmutableBooleanWidget(name, value.toBoolean())
        WritableBoolean -> MutableBooleanWidget(name, value.toBoolean(), onCheckedChange = { sendUpdate(it.toString()) })
        DisplayOnlyDouble -> TextWidget(name, value)
        Button -> ButtonWidget(name, onClick = { sendUpdate("") })
    }
}

/** Converts the value in px to dp. */
@Composable
fun Float.pxToDp() = (this / LocalDensity.current.density).dp