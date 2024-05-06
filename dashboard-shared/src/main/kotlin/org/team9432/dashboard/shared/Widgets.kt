package org.team9432.dashboard.shared

import kotlinx.serialization.Serializable

/** The base of select primitive widgets. */

@Serializable
sealed interface WidgetData: Sendable {
    val name: String
}


/* -------- Immutable Widgets -------- */

/** Represents request to update the value of an immutable string widget. */
@Serializable
data class ImmutableStringWidgetData(override val name: String, val value: String): WidgetData

/** Represents request to update the value of an immutable boolean widget. */
@Serializable
data class ImmutableBooleanWidgetData(override val name: String, val value: Boolean): WidgetData

/** Represents request to update the value of an immutable double widget. */
@Serializable
data class ImmutableDoubleWidgetData(override val name: String, val value: Double): WidgetData


/* -------- Mutable Widgets -------- */

/** Represents request to update the value of a mutable boolean widget. */
@Serializable
data class MutableBooleanWidgetData(override val name: String, val value: Boolean): WidgetData