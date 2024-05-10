package org.team9432.dashboard.lib.widgets.delegates

import org.team9432.dashboard.shared.DoubleUpdate
import org.team9432.dashboard.shared.WidgetPosition
import org.team9432.dashboard.shared.WidgetType

fun readableDashboardDouble(name: String, initialValue: Double, row: Int, col: Int, tab: String, rowsSpanned: Int = 1, colsSpanned: Int = 1) =
    readableDashboardDouble(name, initialValue, WidgetPosition(row, col, tab, rowsSpanned, colsSpanned))

fun readableDashboardDouble(name: String, initialValue: Double, vararg positions: WidgetPosition): GenericWidget<Double> {
    return GenericWidget(
        name,
        WidgetType.ReadableDouble,
        initialValue,
        positions = positions,
        { (it as DoubleUpdate).value },
        { DoubleUpdate(it) }
    )
}