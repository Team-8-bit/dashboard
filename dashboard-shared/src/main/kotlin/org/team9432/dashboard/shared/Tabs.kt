package org.team9432.dashboard.shared

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CreateWidget @OptIn(ExperimentalSerializationApi::class) constructor(
    val name: String,
    @SerialName("widgetType") val type: WidgetType,
    vararg val positions: WidgetPosition,
    @EncodeDefault val id: String = name.hashCode().toString(),
    val initialUpdate: WidgetUpdate,
): Sendable

/** Represents a tab being sent to the dashboard. */
@Serializable
data class Tab(val name: String, val index: Int, val numberOfRows: Int, val numberOfCols: Int): Sendable

/** Represents a widget and its size and position on a given tab. */
@Serializable
data class WidgetPosition(val row: Int, val col: Int, val tab: String, val rowsSpanned: Int = 1, val colsSpanned: Int = 1)