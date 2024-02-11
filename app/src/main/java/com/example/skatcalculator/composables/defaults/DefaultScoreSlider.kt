package com.example.skatcalculator.composables.defaults

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

    var lastValue by remember { mutableFloatStateOf(currentValue) }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .clickable {
                    lastValue = currentValue
                    onClickLeft(
                        if (currentValue + 1f > 120) currentValue
                        else currentValue + 1f
                    )
                }
                .width(60.dp)
        ) {
            Text(
                text = playerName,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Right,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            AnimatedContent(
                targetState = currentValue,
                transitionSpec = {
                                 if (lastValue > currentValue) {
                                     (slideInHorizontally { _ -> -0 } + fadeIn())
                                         .togetherWith(
                                             slideOutHorizontally { width -> width } + fadeOut()
                                         )
                                 } else {
                                     (slideInHorizontally { width -> width } + fadeIn())
                                         .togetherWith(
                                             slideOutHorizontally { _ -> -0 } + fadeOut()
                                         )
                                 }.using(
                                     SizeTransform(clip = false)
                                 )
                },
                label = ""
            ) { target ->
                Text(
                    text = "${target.roundToInt()}",
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Right,
                )
            }
        }
        Slider(
            value = currentValue,
            onValueChange = {
                lastValue = currentValue
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
                    lastValue = currentValue
                    onClickRight(
                        if (currentValue - 1f < 0) maxValue
                        else currentValue - 1f
                    )
                }
                .width(60.dp)
        ) {
            Text(
                text = "Others",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Left,
            )
            AnimatedContent(
                targetState = currentValue,
                transitionSpec = {
                    if (lastValue > currentValue) {
                        (slideInHorizontally { width -> -width } + fadeIn())
                            .togetherWith(
                                slideOutHorizontally { _ -> 0 } + fadeOut()
                            )
                    } else {
                        (slideInHorizontally { _ -> 0 } + fadeIn())
                            .togetherWith(
                                slideOutHorizontally { width -> -width } + fadeOut()
                            )
                    }.using(
                        SizeTransform(clip = false)
                    )
                },
                label = ""
            ) { target ->
                Text(
                    text = "${120 - target.roundToInt()}",
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Left,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewDefaultScoreSlider() {
    var score by remember { mutableFloatStateOf(60f)}
    DefaultScoreSlider(
        playerName = "Florianen",
        currentValue = score,
        onClickLeft = {
                      score = it
        },
        onClickRight = {
                       score = it
        },
        onValueChanged = {
            score = it
        }
    )
}