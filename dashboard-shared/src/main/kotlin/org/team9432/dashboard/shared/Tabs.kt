package org.team9432.dashboard.shared

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CreateWidget(val name: String, @SerialName("widgetType") val type: WidgetType, vararg val positions: WidgetPosition, val initialUpdate: WidgetUpdate): Sendable

/** Represents a tab being sent to the dashboard. */
@Serializable
data class Tab(val name: String, val index: Int, val numberOfRows: Int, val numberOfCols: Int): Sendable

/** Represents a widget and its size and position on a given tab. */
@Serializable
data class WidgetPosition(val row: Int, val col: Int, val tab: String, val rowsSpanned: Int = 1, val colsSpanned: Int = 1)