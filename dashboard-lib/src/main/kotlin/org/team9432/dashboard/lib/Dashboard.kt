package org.team9432.dashboard.lib

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import org.team9432.dashboard.lib.server.Server
import org.team9432.dashboard.lib.widgets.Widget
import org.team9432.dashboard.shared.*
import kotlin.coroutines.CoroutineContext

object Dashboard {
    internal lateinit var coroutineContext: CoroutineContext
        private set
    private lateinit var coroutineScope: CoroutineScope

    suspend fun run(context: CoroutineContext): Unit = coroutineScope {
        Dashboard.coroutineContext = context
        coroutineScope = this

        Server.run()
    }

    private val callbacks = mutableMapOf<String, MutableList<(WidgetUpdate) -> Unit>>()

    fun registerCallbackForWidget(name: String, callback: (WidgetUpdate) -> Unit) {
        println("added callback for $name")
        callbacks.getOrPut(name) { mutableListOf() }.add(callback)
    }

    fun processInformation(sendable: Sendable) {
        when (sendable) {
            is WidgetUpdateRequest -> {
                callbacks[sendable.name]?.forEach { it.invoke(sendable.update) }
            }

            else -> {}
        }
    }

    /* -------- Widgets -------- */

    private val registeredWidgets = mutableListOf<Widget>()
    fun registerWidget(widget: Widget) {
        registeredWidgets.add(widget)
    }

    fun getAllWidgetData() = registeredWidgets.map { it.getCurrentState() }

    fun updateWidget(value: WidgetUpdateRequest) {
        Server.sendToAll(value)
    }

    private val createdWidgets = mutableMapOf<String, CreateWidget>()
    fun getAllWidgets() = createdWidgets.values.toList()

    fun createWidget(name: String, type: WidgetType, vararg positions: WidgetPosition, initialUpdate: WidgetUpdate) {
        val widget = CreateWidget(name, type, positions = positions, initialUpdate)
        createdWidgets[name] = widget
        Server.sendToAll(widget)
    }

    /* -------- Tabs -------- */

    private val currentTabs = mutableMapOf<Int, Tab>()

    fun addTab(name: String, index: Int, rows: Int, cols: Int) {
        assert(!currentTabs.contains(index)) { "There is already a tab at index ${index}!" }

        val tab = Tab(name, index, rows, cols)
        currentTabs[index] = tab
        Server.sendToAll(tab)
    }

    fun getAllTabs() = currentTabs.values.toList()
}