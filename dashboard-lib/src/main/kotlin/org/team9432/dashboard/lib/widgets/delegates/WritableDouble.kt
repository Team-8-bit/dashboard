package org.team9432.dashboard.lib.widgets.delegates

import org.team9432.dashboard.lib.Dashboard
import org.team9432.dashboard.shared.DoubleUpdate
import org.team9432.dashboard.shared.WidgetType

fun writableDashboardDouble(
    title: String, initialValue: Double, row: Int,
    col: Int,
    tab: String,
    rowsSpanned: Int = 1,
    colsSpanned: Int = 1,
    onDashboardChange: ((Double) -> Unit)? = null,
): GenericWidget<Double> {
    if (onDashboardChange != null) Dashboard.registerCallbackForWidget(title.hashCode().toString()) { onDashboardChange.invoke((it as DoubleUpdate).value) }
    return GenericWidget(
        title,
        WidgetType.WritableDouble,
        initialValue,
        row, col, tab, rowsSpanned, colsSpanned,
        { (it as DoubleUpdate).value },
        { DoubleUpdate(it) }
    )
}