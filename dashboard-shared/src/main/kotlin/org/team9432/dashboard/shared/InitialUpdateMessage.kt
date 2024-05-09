package org.team9432.dashboard.shared

import kotlinx.serialization.Serializable

@Serializable
data class InitialUpdateMessage(val tabs: List<Tab>, val widgets: List<Widget>, val widgetData: List<WidgetUpdateRequest>): Sendable
