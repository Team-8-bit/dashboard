package org.team9432.dashboard.lib.widgets.delegates

import org.team9432.dashboard.lib.Dashboard
import org.team9432.dashboard.shared.WidgetPosition
import org.team9432.dashboard.shared.WidgetType
import org.team9432.dashboard.shared.WidgetUpdate
import org.team9432.dashboard.shared.WidgetUpdateRequest
import kotlin.reflect.KProperty

class GenericWidget<T>(
    name: String,
    type: WidgetType,
    initialValue: T,
    vararg positions: WidgetPosition,
    private val getValue: (WidgetUpdate) -> T,
    private val getUpdate: (T) -> WidgetUpdate,
    private val id: String = name.hashCode().toString(),
) {

    init {
        Dashboard.createWidget(name, type, positions = positions, getUpdate(initialValue), id)
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getValue(Dashboard.getWidgetData(id)!!)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (value != getValue(Dashboard.getWidgetData(id)!!)) {
            Dashboard.updateWidget(WidgetUpdateRequest(id, getUpdate(value)))
        }
    }
}