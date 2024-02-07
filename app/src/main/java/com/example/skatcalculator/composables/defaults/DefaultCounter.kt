package com.example.skatcalculator.composables.defaults

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DefaultCounter(
    currentValue: Int = 0,
    minValue: Int = 0,
    maxValue: Int,
    onClick: (Int) -> Unit
) {
    var lastValue by remember{ mutableIntStateOf(currentValue) }
    Box(
        modifier = Modifier
            .clickable {
                lastValue = currentValue
                onClick(if (currentValue + 1 <= maxValue) currentValue + 1 else minValue)
            }
            .size(50.dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = currentValue,
            transitionSpec = {
                if (currentValue > lastValue) {
                    (slideInVertically { height -> -height } + fadeIn())
                        .togetherWith(
                            slideOutVertically { height -> height } + fadeOut()
                        )
                } else {
                    (slideInVertically { height -> height } + fadeIn())
                        .togetherWith(
                            slideOutVertically { height -> -height } + fadeOut()
                        )
                }.using(
                    SizeTransform(clip = false)
                )
            },
            label = ""
        ) {target ->
            Text(
                text = "$target"
            )
        }
    }
}

@Preview
@Composable
fun PreviewDefaultCounter() {
    var value by remember { mutableIntStateOf(0) }
    DefaultCounter(
        currentValue = value,
        maxValue = 3,
        onClick = {
            value = it
        }
    )
}