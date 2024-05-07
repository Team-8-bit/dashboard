package org.team9432.dashboard.shared

import kotlinx.serialization.Serializable

/** The base of select primitive widgets. */

@Serializable
sealed interface WidgetData: Sendable {
    val name: String
}


/* -------- Immutable Widgets -------- */

/** Request to update the value of a display only string widget. */
@Serializable
data class DisplayOnlyStringData(override val name: String, val value: String): WidgetData

/** Request to update the value of a display only boolean widget. */
@Serializable
data class DisplayOnlyBooleanData(override val name: String, val value: Boolean): WidgetData

/** Request to update the value of a display only double widget. */
@Serializable
data class DisplayOnlyDoubleData(override val name: String, val value: Double): WidgetData


/* -------- Mutable Widgets -------- */

/** Request to update the value of a writable boolean widget. */
@Serializable
data class WritableBooleanData(override val name: String, val value: Boolean): WidgetData