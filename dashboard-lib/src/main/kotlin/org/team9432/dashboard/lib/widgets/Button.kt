package org.team9432.dashboard.lib.widgets

import org.team9432.dashboard.lib.Dashboard
import org.team9432.dashboard.shared.ButtonUpdate
import org.team9432.dashboard.shared.WidgetPosition
import org.team9432.dashboard.shared.WidgetType
import org.team9432.dashboard.shared.WidgetUpdateRequest

class DashboardButton(val name: String, onClick: () -> Unit, vararg positions: WidgetPosition): Widget() {
    constructor(name: String, row: Int, col: Int, tab: String, rowsSpanned: Int = 1, colsSpanned: Int = 1, onClick: () -> Unit):
            this(name, onClick, WidgetPosition(row, col, tab, rowsSpanned, colsSpanned))

    init {
        Dashboard.registerCallbackForWidget(name) { onClick() }
        Dashboard.registerWidget(this)
        Dashboard.createWidget(name, WidgetType.Button, positions = positions, ButtonUpdate)
    }

    override fun getCurrentState(): WidgetUpdateRequest {
        return WidgetUpdateRequest(name, ButtonUpdate)
    }
}