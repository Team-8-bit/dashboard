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


    /* -------- Dropdown -------- */

    fun registerDropdown(title: String, options: List<String>, row: Int, col: Int, tab: String, rowsSpanned: Int = 1, colsSpanned: Int = 1, onValueSelected: (String) -> Unit) {
        val id = title.hashCode().toString()
        callbacks[id] = { onValueSelected((it as DropdownSelected).option) }
        createWidget(title, WidgetType.Dropdown, WidgetPosition(row, col, tab, rowsSpanned, colsSpanned), DropdownUpdate(options))
    }


    /* -------- Buttons -------- */

    fun registerButton(title: String, row: Int, col: Int, tab: String, rowsSpanned: Int = 1, colsSpanned: Int = 1, onClick: () -> Unit) {
        val id = title.hashCode().toString()
        callbacks[id] = { onClick() }
        createWidget(title, WidgetType.Button, WidgetPosition(row, col, tab, rowsSpanned, colsSpanned), ButtonUpdate)
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

    fun createWidget(name: String, type: WidgetType, position: WidgetPosition, initialUpdate: WidgetUpdate, id: String = name.hashCode().toString()) {
        val widget = CreateWidget(name, type, position, id, initialUpdate)
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