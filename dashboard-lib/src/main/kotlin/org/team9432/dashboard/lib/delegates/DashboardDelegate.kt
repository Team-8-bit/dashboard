package org.team9432.dashboard.lib.delegates

import org.team9432.dashboard.lib.Dashboard
import org.team9432.dashboard.shared.WidgetUpdate
import kotlin.reflect.KProperty

fun readableStringDashboardWidget(title: String, initialValue: String) = GenericWidget(title, initialValue) { it }
fun readableBooleanDashboardWidget(title: String, initialValue: Boolean) = GenericWidget(title, initialValue) { it.toBoolean() }
fun readableDoubleDashboardWidget(title: String, initialValue: Double) = GenericWidget(title, initialValue) { it.toDouble() }

fun writableBooleanDashboardWidget(title: String, initialValue: Boolean, onDashboardChange: ((Boolean) -> Unit)? = null): GenericWidget<Boolean> {
    if (onDashboardChange != null) Dashboard.registerCallbackForWidget(title.hashCode().toString()) { onDashboardChange.invoke(it.toBoolean()) }
    return GenericWidget(title, initialValue) { it.toBoolean() }
}

fun writableStringDashboardWidget(title: String, initialValue: String, onDashboardChange: ((String) -> Unit)? = null): GenericWidget<String> {
    if (onDashboardChange != null) Dashboard.registerCallbackForWidget(title.hashCode().toString()) { onDashboardChange.invoke(it) }
    return GenericWidget(title, initialValue) { it }
}

fun writableDoubleDashboardWidget(title: String, initialValue: Double, onDashboardChange: ((Double) -> Unit)? = null): GenericWidget<Double> {
    if (onDashboardChange != null) Dashboard.registerCallbackForWidget(title.hashCode().toString()) { onDashboardChange.invoke(it.toDouble()) }
    return GenericWidget(title, initialValue) { it.toDouble() }
}

class GenericWidget<T>(title: String, initialValue: T, private val getValue: (String) -> T) {
    private val id = title.hashCode().toString()

    init {
        Dashboard.updateWidget(WidgetUpdate(id, initialValue.toString()))
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getValue(Dashboard.getWidgetData(id)!!.value)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (value != getValue(Dashboard.getWidgetData(id)!!.value)) {
            Dashboard.updateWidget(WidgetUpdate(id, value.toString()))
        }
    }
}