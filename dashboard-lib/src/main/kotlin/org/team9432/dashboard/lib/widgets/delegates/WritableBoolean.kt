package org.team9432.dashboard.lib.widgets.delegates

import org.team9432.dashboard.lib.Dashboard
import org.team9432.dashboard.shared.BooleanUpdate
import org.team9432.dashboard.shared.WidgetType

fun writableDashboardBoolean(
    title: String, initialValue: Boolean, row: Int,
    col: Int,
    tab: String,
    rowsSpanned: Int = 1,
    colsSpanned: Int = 1,
    onDashboardChange: ((Boolean) -> Unit)? = null,
): GenericWidget<Boolean> {
    if (onDashboardChange != null) Dashboard.registerCallbackForWidget(title.hashCode().toString()) { onDashboardChange.invoke((it as BooleanUpdate).value) }
    return GenericWidget(
        title,
        WidgetType.WritableBoolean,
        initialValue,
        row, col, tab, rowsSpanned, colsSpanned,
        { (it as BooleanUpdate).value },
        { BooleanUpdate(it) }
    )
}