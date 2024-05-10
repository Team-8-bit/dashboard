package org.team9432.dashboard.app.io

import androidx.compose.runtime.mutableStateMapOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.team9432.dashboard.app.ui.widgets.*
import org.team9432.dashboard.shared.*

/** The interface between the app and the networking code. Also tracks the current state of all widgets and tabs. */
object Client {
    private lateinit var coroutineScope: CoroutineScope

    /** Starts and runs the client, must be called before anything else. */
    suspend fun run() = coroutineScope {
        coroutineScope = this
        launch {
            Ktor.run()
        }
    }

    /** Process a newly received piece of information. */
    fun processInformation(sendable: Sendable) {
        when (sendable) {
            is Tab -> addTab(sendable)
            is WidgetUpdateRequest -> allWidgets[sendable.name]?.acceptUpdate(sendable.update)
            is CreateWidget -> createWidget(sendable)
            is InitialUpdateMessage -> {}
        }
    }

    fun reset() {
        widgetsByTab.clear()
        currentTabs.clear()
    }

    /** Forces the current connection to restart. */
    fun reconnect() = Ktor.reconnect()

    /* -------- Widgets -------- */

    /** Updates the state of a widget by sending it to the robot code. */
    fun updateWidget(name: String, update: WidgetUpdate) = Ktor.send(WidgetUpdateRequest(name, update))

    private val allWidgets = mutableMapOf<String, WidgetBase>()

    private fun createWidget(data: CreateWidget) {
        val widgetClass: WidgetBase = when (data.type) {
            WidgetType.ReadableString -> ReadableStringWidget(data)
            WidgetType.ReadableBoolean -> ReadableBooleanWidget(data)
            WidgetType.ReadableDouble -> ReadableDoubleWidget(data)
            WidgetType.WritableString -> WritableStringWidget(data)
            WidgetType.WritableBoolean -> WritableBooleanWidget(data)
            WidgetType.WritableDouble -> WritableDoubleWidget(data)
            WidgetType.Button -> ButtonWidget(data)
            WidgetType.Dropdown -> DropdownWidget(data)
            WidgetType.ReadableBooleanList -> ReadableBooleanListWidget(data)
        }

        widgetClass.acceptUpdate(data.initialUpdate)

        allWidgets[data.name] = widgetClass
        data.positions.forEach { widgetsByTab[it.tab]?.add(widgetClass) }
    }

    /* -------- Tabs -------- */

    /** A map of tab to the widgets that are on it. */
    private val widgetsByTab = mutableMapOf<String, MutableList<WidgetBase>>()
    fun getWidgetsOnTab(tab: String) = widgetsByTab[tab]?.toList() ?: emptyList()

    /** A map of the current tabs. */
    private val currentTabs = mutableStateMapOf<Int, Tab>()

    /** Adds a tab. */
    private fun addTab(tab: Tab) {
        currentTabs[tab.index] = tab
        widgetsByTab[tab.name] = mutableListOf()
    }

    fun getCurrentTabs() = currentTabs
}
