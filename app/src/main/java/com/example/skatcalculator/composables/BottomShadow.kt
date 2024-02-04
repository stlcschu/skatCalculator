package com.example.skatcalculator.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BottomShadow(
    shadowColor: Color = Color.Black,
    alpha: Float = 0.1f,
    height: Dp = 8.dp
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(height)
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    shadowColor.copy(alpha = alpha),
                    Color.Transparent,
                )
            )
        )
    )
}

@Composable
fun GradientBox(
    fromColor: Color,
    fromColorAlpha: Float = 1f,
    toColor: Color = Color.Transparent,
    toColorAlpha: Float = 1f,
    height: Dp = 8.dp,
    modifier: Modifier
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .height(height)
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    fromColor.copy(alpha = fromColorAlpha),
                    toColor.copy(alpha = toColorAlpha),
                )
            )
        )
    )
}