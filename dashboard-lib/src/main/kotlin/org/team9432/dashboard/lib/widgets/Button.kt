package org.team9432.dashboard.lib.widgets

import org.team9432.dashboard.lib.Dashboard
import org.team9432.dashboard.lib.Dashboard.createWidget
import org.team9432.dashboard.shared.ButtonUpdate
import org.team9432.dashboard.shared.WidgetPosition
import org.team9432.dashboard.shared.WidgetType

fun dashboardButton(title: String, row: Int, col: Int, tab: String, rowsSpanned: Int = 1, colsSpanned: Int = 1, onClick: () -> Unit) =
    dashboardButton(title, onClick, WidgetPosition(row, col, tab, rowsSpanned, colsSpanned))

fun dashboardButton(title: String, onClick: () -> Unit, vararg positions: WidgetPosition) {
    val id = title.hashCode().toString()
    Dashboard.registerCallbackForWidget(id) { onClick() }
    createWidget(title, WidgetType.Button, positions = positions, ButtonUpdate)
}