package org.team9432.dashboard.shared

import kotlinx.serialization.Serializable

/** Message that the button with the given name should be displayed or has been pressed. */
@Serializable
data class ButtonData(override val name: String): WidgetData