package org.team9432.dashboard.app.io

import androidx.compose.runtime.mutableStateMapOf
import org.team9432.dashboard.shared.*

/** The interface between the app and the networking code. Also tracks the current state of all widgets and tabs. */
object Client {
    /** Starts and runs the client, must be called before anything else. */
    suspend fun run() = Ktor.run()

    /** Process a newly received piece of information. */
    fun processInformation(sendable: Sendable) {
        when (sendable) {
            is AddTab -> addTab(sendable)
            is RemoveTab -> removeTab(sendable.name)
            is WidgetUpdate -> valueMap[sendable.id] = sendable.value
        }
    }

    /** Forces the current connection to restart. */
    fun reconnect() = Ktor.reconnect()

    /* -------- Widgets -------- */

    /** Map of current widget states. */
    private val valueMap = mutableStateMapOf<String, String>()

    /** The type of each widget. */
    private val widgetTypes = mutableMapOf<String, WidgetType>()
    fun getTypeOfWidget(id: String) = widgetTypes[id]

    /** Gets the widget data with a given name. */
    fun getWidgetData(id: String) = valueMap[id]

    /** Updates the state of a widget by sending it to the robot code. */
    fun updateWidget(widgetUpdate: WidgetUpdate) = Ktor.send(widgetUpdate)


    /* -------- Tabs -------- */

    /** A map of the current tabs. */
    val currentTabs = mutableStateMapOf<String, Tab>()

    /** Adds a tab. */
    private fun addTab(sendable: AddTab) {
        sendable.tab.data.forEach { widgetTypes[it.id] = it.type }
        currentTabs[sendable.name] = sendable.tab
    }

    /** Removes a tab. */
    private fun removeTab(name: String) {
        currentTabs.remove(name)
    }

    /** Gets the widgets that should be displayed on the given tab. */
    fun getWidgetsOnTab(index: Int): List<WidgetDefinition> {
        return currentTabs.values.firstOrNull { it.index == index }?.data ?: emptyList()
    }
}
