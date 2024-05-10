package org.team9432.dashboard.app.ui.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.team9432.dashboard.shared.BooleanListCreate
import org.team9432.dashboard.shared.BooleanListIndividualUpdate
import org.team9432.dashboard.shared.CreateWidget
import org.team9432.dashboard.shared.WidgetUpdate

/** Boolean displayed as a colored box. */
class ReadableBooleanListWidget(data: CreateWidget): WidgetBase(data) {
    private var currentValues = mutableMapOf<String, MutableState<Boolean>>()

    @Composable
    override fun display() {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = data.name, fontSize = 20.sp, textAlign = TextAlign.Center)

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                println(currentValues)
                currentValues.forEach { (name, value) ->
                    Row(Modifier.weight(1F).padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(modifier = Modifier.weight(1F), text = name, fontSize = 17.sp, textAlign = TextAlign.Center)
                        Surface(
                            Modifier.fillMaxSize().weight(1F),
                            color = if (value.value) Color.Green else Color.Red,
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(2.dp, MaterialTheme.colorScheme.onSurface)
                        ) {}
                    }
                }
            }
        }
    }

    override fun acceptUpdate(update: WidgetUpdate) {
        when (update) {
            is BooleanListCreate -> {
                currentValues = update.values.mapValues { mutableStateOf(it.value) }.toMutableMap()
            }

            is BooleanListIndividualUpdate -> {
                currentValues[update.name]?.value = update.value
            }

            else -> {}
        }
    }
}