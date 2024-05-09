package org.team9432.dashboard.lib.widgets

import org.team9432.dashboard.lib.Dashboard
import org.team9432.dashboard.lib.Dashboard.createWidget
import org.team9432.dashboard.shared.*

fun dashboardDropdown(title: String, options: List<String>, row: Int, col: Int, tab: String, rowsSpanned: Int = 1, colsSpanned: Int = 1, onValueSelected: (String) -> Unit) {
    val id = title.hashCode().toString()
    Dashboard.registerCallbackForWidget(id) { onValueSelected((it as DropdownSelected).option) }
    createWidget(title, WidgetType.Dropdown, WidgetPosition(row, col, tab, rowsSpanned, colsSpanned), DropdownUpdate(options))
}