package org.team9432.dashboard.lib.widgets.delegates

import org.team9432.dashboard.lib.Dashboard
import org.team9432.dashboard.lib.widgets.Widget
import org.team9432.dashboard.shared.WidgetPosition
import org.team9432.dashboard.shared.WidgetType
import org.team9432.dashboard.shared.WidgetUpdate
import org.team9432.dashboard.shared.WidgetUpdateRequest
import kotlin.reflect.KProperty

class GenericWidget<T>(
    private val name: String,
    type: WidgetType,
    initialValue: T,
    vararg positions: WidgetPosition,
    private val getValue: (WidgetUpdate) -> T,
    private val getUpdate: (T) -> WidgetUpdate,
): Widget() {
    private var currentState = initialValue

    init {
        Dashboard.createWidget(name, type, positions = positions, getUpdate(initialValue))
        Dashboard.registerCallbackForWidget(name) { currentState = getValue(it) }
        Dashboard.registerWidget(this)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (value != currentState) {
            currentState = value
            Dashboard.updateWidget(WidgetUpdateRequest(name, getUpdate(value)))
        }
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return currentState
    }

    override fun getCurrentState(): WidgetUpdateRequest {
        return WidgetUpdateRequest(name, getUpdate(currentState))
    }
}