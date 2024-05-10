package org.team9432.dashboard.lib.widgets.delegates

import org.team9432.dashboard.lib.Dashboard
import org.team9432.dashboard.shared.StringUpdate
import org.team9432.dashboard.shared.WidgetPosition
import org.team9432.dashboard.shared.WidgetType

fun writableDashboardString(name: String, initialValue: String, row: Int, col: Int, tab: String, rowsSpanned: Int = 1, colsSpanned: Int = 1, onDashboardChange: ((String) -> Unit)? = null) =
    writableDashboardString(name, initialValue, onDashboardChange, WidgetPosition(row, col, tab, rowsSpanned, colsSpanned))

fun writableDashboardString(name: String, initialValue: String, onDashboardChange: ((String) -> Unit)? = null, vararg positions: WidgetPosition): GenericWidget<String> {
    if (onDashboardChange != null) Dashboard.registerCallbackForWidget(name) { onDashboardChange.invoke((it as StringUpdate).value) }
    return GenericWidget(
        name,
        WidgetType.WritableString,
        initialValue,
        positions = positions,
        { (it as StringUpdate).value },
        { StringUpdate(it) }
    )
}