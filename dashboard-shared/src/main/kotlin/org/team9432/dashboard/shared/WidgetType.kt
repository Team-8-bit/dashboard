package org.team9432.dashboard.shared

import kotlinx.serialization.Serializable

@Serializable
data class WidgetUpdateRequest(val name: String, val update: WidgetUpdate): Sendable

@Serializable
sealed interface WidgetUpdate

@Serializable
data class StringUpdate(val value: String): WidgetUpdate

@Serializable
data class BooleanUpdate(val value: Boolean): WidgetUpdate

@Serializable
data class DoubleUpdate(val value: Double): WidgetUpdate

@Serializable
data object ButtonUpdate: WidgetUpdate

@Serializable
data class DropdownUpdate(val options: List<String>): WidgetUpdate

@Serializable
data class DropdownSelected(val option: String): WidgetUpdate

@Serializable
data class BooleanListCreate(val values: Map<String, Boolean>): WidgetUpdate

@Serializable
data class BooleanListIndividualUpdate(val name: String, val value: Boolean): WidgetUpdate

enum class WidgetType {
    ReadableString,
    ReadableBoolean,
    ReadableDouble,
    WritableString,
    WritableBoolean,
    WritableDouble,
    Button,
    Dropdown,
    ReadableBooleanList
}