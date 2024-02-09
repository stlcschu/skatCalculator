package com.example.skatcalculator.composables.defaults

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.skatcalculator.R

@Composable
fun DefaultColumnRow(
    height: Dp,
    color: Color = Color.White,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(color = color),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceAround
    ) {
        content()
    }
}

@Composable
fun DefaultColumnRowClickable(
    height: Dp,
    color: Color = Color.White,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
        .fillMaxWidth()
        .height(height)
        .background(color = color),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceAround
    ) {
        content()
    }
}

@Composable
fun DefaultColumnRowWithButton(
    buttonIcon: Int,
    height: Dp,
    color: Color = Color.White,
    buttonFunction: () -> Unit,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(color = color),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = buttonFunction,
            modifier = Modifier
                .align(Alignment.Top)

        ) {
            Icon(painter = painterResource(id = buttonIcon), contentDescription = "Play history game")
        }
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceAround
        ) {
            content()
        }
    }

}

@Preview
@Composable
fun PreviewDefaultColumnRowClickable() {
    DefaultColumnRowClickable(
        height = 20.dp,
        onClick = {},
    ) {
        Text(text = "fdsfsdfsd")
        Text(text = "fdsfsdfsd")
        Text(text = "fdsfsdfsd")
    }
}

@Preview
@Composable
fun PreviewDefaultColumnRowWithButton() {
    DefaultColumnRowWithButton(
        R.drawable.baseline_play_arrow_24,
        height = 20.dp,
        buttonFunction = {}
    ) {
        Text(text = "fdsfsdfsd")
        Text(text = "fdsfsdfsd")
        Text(text = "fdsfsdfsd")
    }
}