package com.example.skatcalculator.composables.defaults

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.skatcalculator.database.events.SkatRoundEvent
import kotlin.math.roundToInt

@Composable
fun DefaultScoreSlider(
    maxValue: Float = 120f,
    playerName: String,
    currentValue: Float,
    onClickLeft: (Float) -> Unit,
    onClickRight: (Float) -> Unit,
    onValueChanged: (Float) ->  Unit

) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .clickable {
                    onClickLeft(if (currentValue - 1f < 0) currentValue else currentValue - 1f)
                }
                .width(60.dp)
        ) {
            Text(
                text = playerName,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = currentValue.roundToInt().toString(),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Right
            )
        }
        Slider(
            value = currentValue,
            onValueChange = {
                onValueChanged(it)
            },
            valueRange = 0f..maxValue,
            steps = maxValue.roundToInt()-1,
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .padding(end = 5.dp, start = 5.dp)
        )
        Column(
            modifier = Modifier
                .clickable {
                    onClickRight(
                        if (currentValue + 1f > 120) maxValue else currentValue + 1f
                    )
                }
                .width(60.dp)
        ) {
            Text(
                text = "Others",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
            Text(
                text = (120 - currentValue.roundToInt()).toString(),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Left
            )
        }
    }
}