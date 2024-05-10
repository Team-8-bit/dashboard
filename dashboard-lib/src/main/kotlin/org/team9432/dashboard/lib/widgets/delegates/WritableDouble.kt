package org.team9432.dashboard.lib.widgets.delegates

import org.team9432.dashboard.lib.Dashboard
import org.team9432.dashboard.shared.DoubleUpdate
import org.team9432.dashboard.shared.WidgetPosition
import org.team9432.dashboard.shared.WidgetType

fun writableDashboardDouble(name: String, initialValue: Double, row: Int, col: Int, tab: String, rowsSpanned: Int = 1, colsSpanned: Int = 1, onDashboardChange: ((Double) -> Unit)? = null) =
    writableDashboardDouble(name, initialValue, onDashboardChange, WidgetPosition(row, col, tab, rowsSpanned, colsSpanned))

fun writableDashboardDouble(name: String, initialValue: Double, onDashboardChange: ((Double) -> Unit)? = null, vararg positions: WidgetPosition): GenericWidget<Double> {
    if (onDashboardChange != null) Dashboard.registerCallbackForWidget(name) { onDashboardChange.invoke((it as DoubleUpdate).value) }
    return GenericWidget(
        name,
        WidgetType.WritableDouble,
        initialValue,
        positions = positions,
        { (it as DoubleUpdate).value },
        { DoubleUpdate(it) }
    )
}