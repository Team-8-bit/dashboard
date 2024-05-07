package org.team9432.dashboard.lib.delegates

import org.team9432.dashboard.lib.Dashboard
import org.team9432.dashboard.shared.WidgetUpdate
import kotlin.reflect.KProperty

fun immutableStringDashboardWidget(title: String, initialValue: String) = GenericWidget(title, initialValue) { it }
fun immutableBooleanDashboardWidget(title: String, initialValue: Boolean) = GenericWidget(title, initialValue) { it.toBoolean() }
fun immutableDoubleDashboardWidget(title: String, initialValue: Double) = GenericWidget(title, initialValue) { it.toDouble() }

fun mutableBooleanDashboardWidget(title: String, initialValue: Boolean, onDashboardChange: ((Boolean) -> Unit)? = null): GenericWidget<Boolean> {
    if (onDashboardChange != null) Dashboard.registerCallbackForWidget(title) { onDashboardChange.invoke(it as Boolean) }
    return GenericWidget(title, initialValue) { it.toBoolean() }
}

class GenericWidget<T>(private val title: String, initialValue: T, private val getValue: (String) -> T) {
    init {
        Dashboard.updateWidget(WidgetUpdate(title, initialValue.toString()))
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getValue(Dashboard.getWidgetData(title)!!.value)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (value != getValue(Dashboard.getWidgetData(title)!!.value)) {
            Dashboard.updateWidget(WidgetUpdate(title, value.toString()))
        }
    }
}