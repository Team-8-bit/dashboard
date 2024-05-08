package org.team9432.dashboard.app.ui

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.team9432.dashboard.shared.Tab

/** Displays the list of tabs provided by the robot code. */
@Composable
fun TabBar(tabs: List<Tab>, currentTab: Int, onTabChange: (Int) -> Unit) {
    TabRow(currentTab, modifier = Modifier) {
        tabs.forEach { (name, index) ->
            Tab(
                selected = index == currentTab,
                onClick = { onTabChange(index) },
                text = { Text(name) }
            )
        }
    }
}