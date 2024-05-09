package org.team9432.dashboard.app.ui.widgets

import androidx.compose.runtime.Composable
import org.team9432.dashboard.app.io.Client
import org.team9432.dashboard.shared.CreateWidget
import org.team9432.dashboard.shared.WidgetUpdate

abstract class WidgetBase(val data: CreateWidget) {
    abstract fun acceptUpdate(update: WidgetUpdate)
    fun sendUpdate(update: WidgetUpdate) = Client.updateWidget(data.id, update)

    @Composable
    abstract fun display()
}