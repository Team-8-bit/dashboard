package org.team9432.dashboard.app.io

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import org.team9432.dashboard.shared.*

/** The interface between the app and the networking code. Also tracks the current state of all widgets and tabs. */
object Client {
    /** Starts and runs the client, must be called before anything else. */
    suspend fun run() = Ktor.run()

    /** Process a newly received piece of information. */
    fun processInformation(sendable: Sendable) {
        when (sendable) {
            is Tab -> addTab(sendable)
            is WidgetUpdateRequest -> valueMap.getOrPut(sendable.id, defaultValue = { mutableStateOf(sendable.update) }).value = sendable.update
            is Widget -> createWidget(sendable)
            is InitialUpdateMessage -> {}
        }
    }

    fun reset() {
        valueMap.clear()
        widgetsByTab.clear()
        currentTabs.clear()
    }

    /** Forces the current connection to restart. */
    fun reconnect() = Ktor.reconnect()

    /* -------- Widgets -------- */

    /** Map of current widget states. */
    private val valueMap = mutableMapOf<String, MutableState<WidgetUpdate>>()

    /** Gets the widget data with a given name. */
    fun getWidgetData(id: String) = valueMap[id]

    /** Updates the state of a widget by sending it to the robot code. */
    fun updateWidget(id: String, update: WidgetUpdate) = Ktor.send(WidgetUpdateRequest(id, update))

    fun createWidget(widget: Widget) {
        widgetsByTab[widget.position.tab]?.add(widget)
    }

    /* -------- Tabs -------- */

    /** A map of tab to the widgets that are on it. */
    private val widgetsByTab = mutableMapOf<String, MutableList<Widget>>()
    fun getWidgetsOnTab(tab: String) = widgetsByTab[tab] ?: emptyList()

    /** A map of the current tabs. */
    private val currentTabs = mutableStateMapOf<Int, Tab>()

    /** Adds a tab. */
    private fun addTab(tab: Tab) {
        currentTabs[tab.index] = tab
        widgetsByTab[tab.name] = mutableListOf()
    }

    fun getCurrentTabs() = currentTabs.values.toList()
}
