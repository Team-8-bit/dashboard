package org.team9432.dashboard.lib.widgets.delegates

import org.team9432.dashboard.shared.BooleanUpdate
import org.team9432.dashboard.shared.WidgetPosition
import org.team9432.dashboard.shared.WidgetType

fun readableDashboardBoolean(title: String, initialValue: Boolean, row: Int, col: Int, tab: String, rowsSpanned: Int = 1, colsSpanned: Int = 1) =
    readableDashboardBoolean(title, initialValue, WidgetPosition(row, col, tab, rowsSpanned, colsSpanned))

fun readableDashboardBoolean(title: String, initialValue: Boolean, vararg positions: WidgetPosition): GenericWidget<Boolean> {
    return GenericWidget(
        title,
        WidgetType.ReadableBoolean,
        initialValue,
        positions = positions,
        { (it as BooleanUpdate).value },
        { BooleanUpdate(it) }
    )
}
