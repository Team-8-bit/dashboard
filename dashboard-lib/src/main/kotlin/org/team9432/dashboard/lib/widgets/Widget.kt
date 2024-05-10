package org.team9432.dashboard.lib.widgets

import org.team9432.dashboard.shared.WidgetUpdateRequest

abstract class Widget {
    abstract fun getCurrentState(): WidgetUpdateRequest
}