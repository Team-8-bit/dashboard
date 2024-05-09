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
import org.team9432.dashboard.shared.*
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
        WidgetArea(tabs.firstOrNull { it.index == currentTab } ?: Tab("", 0, 0, 0))
    }
}

@Composable
private fun WidgetArea(tab: Tab) {
    Surface(Modifier.fillMaxSize()) {
        Box(Modifier.fillMaxSize().onGloballyPositioned { widgetAreaY = it.size.height.toFloat(); widgetAreaX = it.size.width.toFloat() }) {
            val xPerUnit = widgetAreaX.pxToDp() / tab.numberOfCols
            val yPerUnit = widgetAreaY.pxToDp() / tab.numberOfRows

            Client.getWidgetsOnTab(tab.name).forEach {
                Widget(
                    it.name,
                    it.type,
                    Client.getWidgetData(it.id),
                    WidgetPositionInfo(it.position, xPerUnit, yPerUnit),
                    sendUpdate = { message -> Client.updateWidget(it.id, message) }
                )
            }
        }
    }
}

/** Displays a widget of a given size at the given position. */
@Composable
fun Widget(name: String, type: WidgetType, state: MutableState<WidgetUpdate>?, position: WidgetPositionInfo, sendUpdate: (WidgetUpdate) -> Unit) {
    WidgetBase(position) {
        if (state != null) {
            display(name, state.value, type, sendUpdate)
        } else {
            ReadableStringWidget(name, "missing value")
        }
    }
}

/** Finds and displays the correct type of widget by the given name. */
@Composable
fun display(name: String, value: WidgetUpdate, widgetType: WidgetType, sendUpdate: (WidgetUpdate) -> Unit) {
    when (widgetType) {
        ReadableString -> ReadableStringWidget(name, (value as StringUpdate).value)
        ReadableBoolean -> ReadableBooleanWidget(name, (value as BooleanUpdate).value)
        ReadableDouble -> ReadableStringWidget(name, (value as DoubleUpdate).value.toString())
        WritableString -> WritableStringWidget(name, (value as StringUpdate).value, onValueChange = { sendUpdate(StringUpdate(it)) })
        WritableBoolean -> WritableBooleanWidget(name, (value as BooleanUpdate).value, onCheckedChange = { sendUpdate(BooleanUpdate(it)) })
        WritableDouble -> WritableDoubleWidget(name, (value as DoubleUpdate).value) { sendUpdate(DoubleUpdate(it)) }
        Button -> ButtonWidget(name, onClick = { sendUpdate(ButtonUpdate) })
    }
}

/** Converts the value in px to dp. */
@Composable
fun Float.pxToDp() = (this / LocalDensity.current.density).dp