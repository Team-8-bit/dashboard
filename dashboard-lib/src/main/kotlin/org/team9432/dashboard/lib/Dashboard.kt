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

    private val callbacks = mutableMapOf<String, (WidgetData) -> Unit>()

    fun registerCallbackForWidget(name: String, callback: (WidgetData) -> Unit) {
        callbacks[name] = callback
    }

    fun processInformation(sendable: Sendable) {
        when (sendable) {
            is WidgetData -> {
                when (sendable) {
                    is ButtonData -> {
                        callbacks[sendable.name]?.invoke(sendable)
                    }

                    is DisplayOnlyDoubleData, is DisplayOnlyStringData, is DisplayOnlyBooleanData -> {
                        currentValues[sendable.name] = sendable
                    }

                    is WritableBooleanData -> {
                        currentValues[sendable.name] = sendable
                        callbacks[sendable.name]?.invoke(sendable)
                    }
                }
            }

            else -> {}
        }
    }


    /* -------- Buttons -------- */

    fun registerButton(name: String, onClick: () -> Unit) {
        callbacks[name] = { onClick() }
        updateWidget(ButtonData(name))
    }


    /* -------- Widgets -------- */

    private val currentValues = mutableMapOf<String, WidgetData>()

    fun updateWidget(value: WidgetData) {
        currentValues[value.name] = value
        Server.sendToAll(value)
    }

    fun getWidgetData(name: String) = currentValues[name]
    fun getAllWidgetData() = currentValues.values.toList()


    /* -------- Tabs -------- */

    private val currentTabs = mutableMapOf<String, Tab>()

    fun addTab(tab: Tab) {
        assert(currentTabs.none { it.value.index == tab.index }) { "There is already a tab at index ${tab.index}!" }
        currentTabs[tab.name] = tab
        Server.sendToAll(AddTab(tab.name, tab))
    }

    fun removeTab(name: String) {
        currentTabs.remove(name)
        Server.sendToAll(RemoveTab(name))
    }

    fun getAllTabs() = currentTabs.values.toList()
}