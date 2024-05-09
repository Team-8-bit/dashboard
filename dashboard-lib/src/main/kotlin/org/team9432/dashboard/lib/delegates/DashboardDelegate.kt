package org.team9432.dashboard.lib.delegates

import org.team9432.dashboard.lib.Dashboard
import org.team9432.dashboard.shared.*
import kotlin.reflect.KProperty

class GenericWidget<T>(
    name: String,
    type: WidgetType,
    initialValue: T,
    row: Int,
    col: Int,
    tab: String,
    rowsSpanned: Int = 1,
    colsSpanned: Int = 1,
    private val getValue: (WidgetUpdate) -> T,
    private val getUpdate: (T) -> WidgetUpdate,
    private val id: String = name.hashCode().toString(),
) {

    init {
        Dashboard.createWidget(name, type, WidgetPosition(row, col, tab, rowsSpanned, colsSpanned), getUpdate(initialValue), id)
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

fun readableStringDashboardWidget(title: String, initialValue: String, row: Int, col: Int, tab: String, rowsSpanned: Int = 1, colsSpanned: Int = 1): GenericWidget<String> {
    return GenericWidget(
        title,
        WidgetType.ReadableString,
        initialValue,
        row, col, tab, rowsSpanned, colsSpanned,
        { (it as StringUpdate).value },
        { StringUpdate(it) }
    )
}

fun readableBooleanDashboardWidget(title: String, initialValue: Boolean, row: Int, col: Int, tab: String, rowsSpanned: Int = 1, colsSpanned: Int = 1): GenericWidget<Boolean> {
    return GenericWidget(
        title,
        WidgetType.ReadableBoolean,
        initialValue,
        row, col, tab, rowsSpanned, colsSpanned,
        { (it as BooleanUpdate).value },
        { BooleanUpdate(it) }
    )
}

fun readableDoubleDashboardWidget(title: String, initialValue: Double, row: Int, col: Int, tab: String, rowsSpanned: Int = 1, colsSpanned: Int = 1): GenericWidget<Double> {
    return GenericWidget(
        title,
        WidgetType.ReadableDouble,
        initialValue,
        row, col, tab, rowsSpanned, colsSpanned,
        { (it as DoubleUpdate).value },
        { DoubleUpdate(it) }
    )
}

fun writableStringDashboardWidget(
    title: String,
    initialValue: String,
    row: Int,
    col: Int,
    tab: String,
    rowsSpanned: Int = 1,
    colsSpanned: Int = 1,
    onDashboardChange: ((String) -> Unit)? = null,
): GenericWidget<String> {
    if (onDashboardChange != null) Dashboard.registerCallbackForWidget(title.hashCode().toString()) { onDashboardChange.invoke((it as StringUpdate).value) }
    return GenericWidget(
        title,
        WidgetType.WritableString,
        initialValue,
        row, col, tab, rowsSpanned, colsSpanned,
        { (it as StringUpdate).value },
        { StringUpdate(it) }
    )
}

fun writableBooleanDashboardWidget(
    title: String, initialValue: Boolean, row: Int,
    col: Int,
    tab: String,
    rowsSpanned: Int = 1,
    colsSpanned: Int = 1,
    onDashboardChange: ((Boolean) -> Unit)? = null,
): GenericWidget<Boolean> {
    if (onDashboardChange != null) Dashboard.registerCallbackForWidget(title.hashCode().toString()) { onDashboardChange.invoke((it as BooleanUpdate).value) }
    return GenericWidget(
        title,
        WidgetType.WritableBoolean,
        initialValue,
        row, col, tab, rowsSpanned, colsSpanned,
        { (it as BooleanUpdate).value },
        { BooleanUpdate(it) }
    )
}

fun writableDoubleDashboardWidget(
    title: String, initialValue: Double, row: Int,
    col: Int,
    tab: String,
    rowsSpanned: Int = 1,
    colsSpanned: Int = 1,
    onDashboardChange: ((Double) -> Unit)? = null,
): GenericWidget<Double> {
    if (onDashboardChange != null) Dashboard.registerCallbackForWidget(title.hashCode().toString()) { onDashboardChange.invoke((it as DoubleUpdate).value) }
    return GenericWidget(
        title,
        WidgetType.WritableDouble,
        initialValue,
        row, col, tab, rowsSpanned, colsSpanned,
        { (it as DoubleUpdate).value },
        { DoubleUpdate(it) }
    )
}
