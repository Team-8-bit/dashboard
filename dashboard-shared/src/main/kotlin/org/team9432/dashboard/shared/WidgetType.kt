package org.team9432.dashboard.shared

import kotlinx.serialization.Serializable

@Serializable
data class WidgetUpdate(val name: String, val value: String): Sendable

enum class WidgetType {
    DisplayOnlyString,
    DisplayOnlyBoolean,
    DisplayOnlyDouble,
    WritableBoolean,
    Button,
}