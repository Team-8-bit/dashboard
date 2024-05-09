package org.team9432.dashboard.app.ui.widgets.util

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.reflect.KProperty

class SafeMutableState<T>(initialValue: T) {
    private var state by mutableStateOf<T?>(initialValue)

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return try {
            state
        } catch (_: Exception) {
            null
        }
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        state = value
    }
}
