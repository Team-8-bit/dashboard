package org.team9432.dashboard.shared

import kotlinx.serialization.Serializable

/** The base of select primitive widgets. */

@Serializable
sealed interface WidgetData: Sendable {
    val name: String
}

/** Represents a simple update of a string widget being sent to the dashboard. */
@Serializable
data class ImmutableStringWidgetData(override val name: String, val value: String): WidgetData

/** Represents a simple update of a boolean widget being sent to the dashboard. */
@Serializable
data class ImmutableBooleanWidgetData(override val name: String, val value: Boolean): WidgetData

/** Represents a simple update of a boolean widget being sent to or from the dashboard. */
@Serializable
data class MutableBooleanWidgetData(override val name: String, val value: Boolean): WidgetData

/** Represents a simple update of a double widget being sent to the dashboard. */
@Serializable
data class ImmutableDoubleWidgetData(override val name: String, val value: Double): WidgetData