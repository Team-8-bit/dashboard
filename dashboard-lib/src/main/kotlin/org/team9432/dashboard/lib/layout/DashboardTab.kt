package org.team9432.dashboard.lib.layout

import org.team9432.dashboard.shared.Tab
import org.team9432.dashboard.shared.WidgetDefinition
import org.team9432.dashboard.shared.WidgetPosition
import org.team9432.dashboard.shared.WidgetType

class DashboardTab(private val rows: Int, private val cols: Int, private val index: Int, private val name: String) {
    private data class Coordinate(val row: Int, val col: Int)

    private val rowIndices = 0..<rows
    private val colIndices = 0..<cols

    private val usedCoordinates = mutableMapOf<Coordinate, String>()
    private val registeredWidgets = mutableListOf<Pair<WidgetDefinition, WidgetPosition>>()

    fun addWidget(row: Int, col: Int, name: String, type: WidgetType, rowsSpanned: Int = 1, colsSpanned: Int = 1) {
        require(row + (rowsSpanned - 1) in rowIndices && col + (colsSpanned - 1) in colIndices) { "This widget does not fit within the grid!" }

        val rowsUsed = row..<(row + rowsSpanned)
        val colsUsed = col..<(col + colsSpanned)

        val coordinatesUsed = mutableListOf<Coordinate>()
        rowsUsed.forEach { rowIndex ->
            colsUsed.forEach { colIndex ->
                val coordinate = Coordinate(rowIndex, colIndex)
                coordinatesUsed.add(coordinate)

                val value = usedCoordinates[coordinate]
                if (value != null) {
                    throw Exception("The coordinate (row=$rowIndex, col=$colIndex) is already occupied by $value!")
                }
            }
        }

        coordinatesUsed.forEach {
            usedCoordinates[it] = name
        }

        registeredWidgets.add(WidgetDefinition(name, name.hashCode().toString(), type) to WidgetPosition(row, col, rowsSpanned, colsSpanned))
    }

    fun getSendable() = Tab(name, index, rows, cols, registeredWidgets)
}