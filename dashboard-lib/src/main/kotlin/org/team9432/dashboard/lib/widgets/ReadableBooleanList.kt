package org.team9432.dashboard.lib.widgets

import org.team9432.dashboard.lib.Dashboard
import org.team9432.dashboard.shared.*

class ReadableDashboardBooleanList(val name: String, values: Map<String, Boolean>, vararg positions: WidgetPosition): Widget() {
    constructor(name: String, values: Map<String, Boolean>, row: Int, col: Int, tab: String, rowsSpanned: Int = 1, colsSpanned: Int = 1):
            this(name, values, WidgetPosition(row, col, tab, rowsSpanned, colsSpanned))

    init {
        Dashboard.createWidget(name, WidgetType.ReadableBooleanList, positions = positions, BooleanListCreate(values))
        Dashboard.registerWidget(this)
    }

    private val internalMap = values.toMutableMap()

    operator fun set(name: String, value: Boolean) {
        assert(internalMap.contains(name)) { "$name was not found in this widget!" }

        if (internalMap[name] != value) {
            internalMap[name] = value
            Dashboard.updateWidget(WidgetUpdateRequest(this.name, BooleanListIndividualUpdate(name, value)))
        }
    }

    operator fun get(name: String): Boolean {
        return internalMap.getValue(name)
    }

    override fun getCurrentState(): WidgetUpdateRequest {
        return WidgetUpdateRequest(name, BooleanListCreate(internalMap))
    }
}