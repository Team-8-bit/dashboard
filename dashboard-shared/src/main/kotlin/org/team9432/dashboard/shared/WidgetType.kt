package org.team9432.dashboard.shared

import kotlinx.serialization.Serializable

@Serializable
data class WidgetUpdate(val id: String, val value: String): Sendable

enum class WidgetType {
    ReadableString,
    ReadableBoolean,
    ReadableDouble,
    WritableBoolean,
    WritableString,
    WritableDouble,
    Button,
}