package org.team9432.dashboard.lib

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import org.team9432.dashboard.lib.server.Server
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

    private val callbacks = mutableMapOf<String, (WidgetUpdate) -> Unit>()

    fun registerCallbackForWidget(id: String, callback: (WidgetUpdate) -> Unit) {
        callbacks[id] = callback
    }

    fun processInformation(sendable: Sendable) {
        when (sendable) {
            is WidgetUpdateRequest -> {
                currentValues[sendable.id] = sendable.update
                callbacks[sendable.id]?.invoke(sendable.update)
            }

            else -> {}
        }
    }

    /* -------- Widgets -------- */

    private val currentValues = mutableMapOf<String, WidgetUpdate>()

    fun updateWidget(value: WidgetUpdateRequest) {
        currentValues[value.id] = value.update
        Server.sendToAll(value)
    }

    fun getWidgetData(name: String) = currentValues[name]
    fun getAllWidgetData() = currentValues.map { WidgetUpdateRequest(it.key, it.value) }

    private val createdWidgets = mutableMapOf<String, CreateWidget>()
    fun getAllWidgets() = createdWidgets.values.toList()

    fun createWidget(name: String, type: WidgetType, vararg positions: WidgetPosition, initialUpdate: WidgetUpdate, id: String = name.hashCode().toString()) {
        val widget = CreateWidget(name, type, positions = positions, id, initialUpdate)
        currentValues[id] = initialUpdate
        createdWidgets[id] = widget
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