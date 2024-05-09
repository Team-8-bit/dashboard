package org.team9432.dashboard.lib.widgets.delegates

import org.team9432.dashboard.lib.Dashboard
import org.team9432.dashboard.shared.StringUpdate
import org.team9432.dashboard.shared.WidgetType

fun writableDashboardString(
    title: String,
    initialValue: String,
    row: Int,
    col: Int,
    tab: String,
    rowsSpanned: Int = 1,
    colsSpanned: Int = 1,
    onDashboardChange: ((String) -> Unit)? = null,
): GenericWidget<String> {
    if (onDashboardChange != null) Dashboard.registerCallbackForWidget(title.hashCode().toString()) { onDashboardChange.invoke((it as StringUpdate).value) }
    return GenericWidget(
        title,
        WidgetType.WritableString,
        initialValue,
        row, col, tab, rowsSpanned, colsSpanned,
        { (it as StringUpdate).value },
        { StringUpdate(it) }
    )
}