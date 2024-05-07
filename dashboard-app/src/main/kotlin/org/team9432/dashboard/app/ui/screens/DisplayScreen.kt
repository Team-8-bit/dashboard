package org.team9432.dashboard.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import org.team9432.dashboard.app.io.Client
import org.team9432.dashboard.app.ui.TabBar
import org.team9432.dashboard.app.ui.widgets.*
import org.team9432.dashboard.shared.*

// Number of rows and columns to display
private var numberOfCols = 10 // X
private var numberOfRows = 6 // Y

// X and Y dimensions of the widget area
var widgetAreaY by mutableFloatStateOf(0F)
var widgetAreaX by mutableFloatStateOf(0F)

/** Displays the given widgets. */
@Composable
fun DisplayScreen(widgets: List<TabWidget>) {
    Column {
        TabBar()
        Surface(Modifier.fillMaxSize()) {
            Box(Modifier.fillMaxSize().onGloballyPositioned { widgetAreaY = it.size.height.toFloat(); widgetAreaX = it.size.width.toFloat() }) {
                widgets.forEach { widgetData -> Widget(widgetData) }
            }
        }
    }
}

/** Displays a widget of a given size at the given position. */
@Composable
fun Widget(data: TabWidget) {
    val xPerUnit = widgetAreaX.pxToDp() / numberOfCols
    val yPerUnit = widgetAreaY.pxToDp() / numberOfRows

    WidgetBase(
        Modifier
            .width(xPerUnit * data.colsSpanned)
            .height(yPerUnit * data.rowsSpanned)
            .offset(x = xPerUnit * data.col, y = yPerUnit * data.row)
    ) {
        if (data.name != "%empty") {
            display(data.name)
        }
    }
}

/** Finds and displays the correct type of widget by the given name. */
@Composable
fun display(name: String) {
    when (val value = Client.getWidgetData(name)) {
        is DisplayOnlyStringData -> TextWidget(value.name, value.value)
        is DisplayOnlyBooleanData -> ImmutableBooleanWidget(value.name, value.value)
        is WritableBooleanData -> MutableBooleanWidget(value.name, value.value)
        is DisplayOnlyDoubleData -> TextWidget(value.name, value.value.toString())
        is ButtonData -> ButtonWidget(value.name)
        null -> TextWidget(name, "missing value")
    }
}

/** Converts the value in px to dp. */
@Composable
fun Float.pxToDp() = (this / LocalDensity.current.density).dp