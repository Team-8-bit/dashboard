package org.team9432.dashboard.shared

import kotlinx.serialization.Serializable

/** Represents a request to add a tab to the dashboard. */
@Serializable
data class AddTab(val name: String, val tab: Tab): Sendable

/** Represents a request to remove a tab from the dashboard. */
@Serializable
data class RemoveTab(val name: String): Sendable

/** Represents a tab being sent to the dashboard. */
@Serializable
data class Tab(val name: String, val index: Int, val numberOfRows: Int, val numberOfCols: Int, val widgets: List<Pair<WidgetDefinition, WidgetPosition>>)

/** Represents all the information about a widget. */
@Serializable
data class WidgetDefinition(val name: String, val id: String, val type: WidgetType)

/** Represents a widget and its size and position on a given tab. */
@Serializable
data class WidgetPosition(val row: Int, val col: Int, val rowsSpanned: Int, val colsSpanned: Int)