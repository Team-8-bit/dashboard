package org.team9432.dashboard.lib.widgets

import org.team9432.dashboard.lib.Dashboard
import org.team9432.dashboard.lib.Dashboard.createWidget
import org.team9432.dashboard.shared.DropdownSelected
import org.team9432.dashboard.shared.DropdownUpdate
import org.team9432.dashboard.shared.WidgetPosition
import org.team9432.dashboard.shared.WidgetType

fun dashboardDropdown(title: String, options: List<String>, row: Int, col: Int, tab: String, rowsSpanned: Int = 1, colsSpanned: Int = 1, onValueSelected: (String) -> Unit) =
    dashboardDropdown(title, options, onValueSelected, WidgetPosition(row, col, tab, rowsSpanned, colsSpanned))

fun dashboardDropdown(title: String, options: List<String>, onValueSelected: (String) -> Unit, vararg positions: WidgetPosition) {
    val id = title.hashCode().toString()
    Dashboard.registerCallbackForWidget(id) { onValueSelected((it as DropdownSelected).option) }
    createWidget(title, WidgetType.Dropdown, positions = positions, DropdownUpdate(options))
}