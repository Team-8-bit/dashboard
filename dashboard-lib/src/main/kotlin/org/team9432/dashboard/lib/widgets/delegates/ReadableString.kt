package org.team9432.dashboard.lib.widgets.delegates

import org.team9432.dashboard.shared.StringUpdate
import org.team9432.dashboard.shared.WidgetPosition
import org.team9432.dashboard.shared.WidgetType

fun readableDashboardString(name: String, initialValue: String, row: Int, col: Int, tab: String, rowsSpanned: Int = 1, colsSpanned: Int = 1) =
    readableDashboardString(name, initialValue, WidgetPosition(row, col, tab, rowsSpanned, colsSpanned))

fun readableDashboardString(name: String, initialValue: String, vararg positions: WidgetPosition): GenericWidget<String> {
    return GenericWidget(
        name,
        WidgetType.ReadableString,
        initialValue,
        positions = positions,
        { (it as StringUpdate).value },
        { StringUpdate(it) }
    )
}