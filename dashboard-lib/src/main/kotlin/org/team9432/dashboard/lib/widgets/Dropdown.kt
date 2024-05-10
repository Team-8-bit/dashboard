package org.team9432.dashboard.lib.widgets

import org.team9432.dashboard.lib.Dashboard
import org.team9432.dashboard.shared.*

class DashboardDropdown(val name: String, private val options: List<String>, onValueSelected: (String) -> Unit, vararg positions: WidgetPosition): Widget() {
    constructor(name: String, options: List<String>, row: Int, col: Int, tab: String, rowsSpanned: Int = 1, colsSpanned: Int = 1, onValueSelected: (String) -> Unit):
            this(name, options, onValueSelected, WidgetPosition(row, col, tab, rowsSpanned, colsSpanned))

    init {
        Dashboard.registerCallbackForWidget(name) { onValueSelected((it as DropdownSelected).option) }
        Dashboard.registerWidget(this)
        Dashboard.createWidget(name, WidgetType.Dropdown, positions = positions, DropdownUpdate(options))
    }

    override fun getCurrentState(): WidgetUpdateRequest {
        return WidgetUpdateRequest(name, DropdownUpdate(options))
    }
}