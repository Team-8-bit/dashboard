package org.team9432.dashboard.lib.widgets.delegates

import org.team9432.dashboard.shared.DoubleUpdate
import org.team9432.dashboard.shared.WidgetType

fun readableDashboardDouble(title: String, initialValue: Double, row: Int, col: Int, tab: String, rowsSpanned: Int = 1, colsSpanned: Int = 1): GenericWidget<Double> {
    return GenericWidget(
        title,
        WidgetType.ReadableDouble,
        initialValue,
        row, col, tab, rowsSpanned, colsSpanned,
        { (it as DoubleUpdate).value },
        { DoubleUpdate(it) }
    )
}