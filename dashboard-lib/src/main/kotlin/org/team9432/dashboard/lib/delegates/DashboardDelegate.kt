package org.team9432.dashboard.lib.delegates

import org.team9432.dashboard.lib.Dashboard
import org.team9432.dashboard.shared.*
import kotlin.reflect.KProperty

fun immutableStringDashboardWidget(title: String, initialValue: String) = GenericWidget(title, initialValue, { ImmutableStringWidgetData(title, it) }, { (it as ImmutableStringWidgetData).value })
fun immutableBooleanDashboardWidget(title: String, initialValue: Boolean) = GenericWidget(title, initialValue, { ImmutableBooleanWidgetData(title, it) }, { (it as ImmutableBooleanWidgetData).value })
fun immutableDoubleDashboardWidget(title: String, initialValue: Double) = GenericWidget(title, initialValue, { ImmutableDoubleWidgetData(title, it) }, { (it as ImmutableDoubleWidgetData).value })

fun mutableBooleanDashboardWidget(title: String, initialValue: Boolean, onDashboardChange: ((Boolean) -> Unit)? = null): GenericWidget<Boolean> {
    if (onDashboardChange != null) Dashboard.registerCallbackForWidget(title) { onDashboardChange.invoke((it as MutableBooleanWidgetData).value) }
    return GenericWidget(title, initialValue, { MutableBooleanWidgetData(title, it) }, { (it as MutableBooleanWidgetData).value })
}

class GenericWidget<T>(private val title: String, initialValue: T, private val getWidget: (T) -> WidgetData, private val getValue: (WidgetData?) -> T) {
    init {
        Dashboard.updateWidget(getWidget(initialValue))
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getValue(Dashboard.getWidgetData(title))
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (value != getValue(Dashboard.getWidgetData(title))) {
            Dashboard.updateWidget(getWidget(value))
        }
    }
}