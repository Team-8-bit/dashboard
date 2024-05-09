package org.team9432.dashboard.lib.widgets.delegates

import org.team9432.dashboard.shared.StringUpdate
import org.team9432.dashboard.shared.WidgetType

fun readableDashboardString(title: String, initialValue: String, row: Int, col: Int, tab: String, rowsSpanned: Int = 1, colsSpanned: Int = 1): GenericWidget<String> {
    return GenericWidget(
        title,
        WidgetType.ReadableString,
        initialValue,
        row, col, tab, rowsSpanned, colsSpanned,
        { (it as StringUpdate).value },
        { StringUpdate(it) }
    )
}