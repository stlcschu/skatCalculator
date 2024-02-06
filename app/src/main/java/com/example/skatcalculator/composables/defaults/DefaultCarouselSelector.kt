package com.example.skatcalculator.composables.defaults

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.skatcalculator.R
import com.example.skatcalculator.helper.SampleRepository

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun DefaultCarouselSelector(
    values: List<String>,
    contentWidth: Dp = 90.dp,
    currentSelected: Int,
    onValueChanged: (Int) -> Unit
) {

    val density = LocalDensity.current

    val swipeableState = remember {
        AnchoredDraggableState(
            initialValue = currentSelected,
            positionalThreshold = {
                distance: Float -> distance * 0.5f
                                  },
            velocityThreshold = {
                                with(density) { 100.dp.toPx() }
            },
            animationSpec = tween()
        )
    }

    val anchors = with(density) {
        DraggableAnchors {
            0 at -60.dp.toPx()
            1 at 0.dp.toPx()
            2 at 60.dp.toPx()
        }
    }

    SideEffect {
        swipeableState.updateAnchors(anchors)
    }

    if (currentSelected != swipeableState.currentValue) {
        onValueChanged(swipeableState.currentValue)
    }

    val fontSizeOne by animateFloatAsState(
        targetValue = if (currentSelected == 0) 4.5f else 3f,
        animationSpec = tween(500),
        label = "fontSizeOne"
    )
    val fontSizeTwo by animateFloatAsState(
        targetValue = if (currentSelected == 1) 4.5f else 3f,
        animationSpec = tween(500),
        label = "fontSizeTwo"
    )
    val fontSizeThree by animateFloatAsState(
        targetValue = if (currentSelected == 2) 4.5f else 3f,
        animationSpec = tween(500),
        label = "fontSizeThree"
    )

    val fontColorOne by animateColorAsState(
        targetValue = if (currentSelected == 0) colorResource(id = R.color.gunmetal)
        else colorResource(id = R.color.Davys_gray),
        animationSpec = tween(500),
        label = "fontColorOne"
    )
    val fontColorTwo by animateColorAsState(
        targetValue = if (currentSelected == 1) colorResource(id = R.color.gunmetal)
        else colorResource(id = R.color.Davys_gray),
        animationSpec = tween(500),
        label = "fontColorTwo"
    )
    val fontColorThree by animateColorAsState(
        targetValue = if (currentSelected == 2) colorResource(id = R.color.gunmetal)
        else colorResource(id = R.color.Davys_gray),
        animationSpec = tween(500),
        label = "fontColorThree"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .anchoredDraggable(
                swipeableState,
                Orientation.Horizontal
            ),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .width(contentWidth)
                .fillMaxHeight()
                .offset {
                    IntOffset(
                        swipeableState
                            .requireOffset()
                            .toInt(), 0
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = values[0],
                textAlign = TextAlign.Center,
                fontSize = fontSizeThree.em,
                color = fontColorThree,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Box(
            modifier = Modifier
                .width(contentWidth)
                .fillMaxHeight()
                .offset {
                    IntOffset(
                        swipeableState
                            .requireOffset()
                            .toInt(), 0
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = values[1],
                textAlign = TextAlign.Center,
                fontSize = fontSizeTwo.em,
                color = fontColorTwo,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Box(
            modifier = Modifier
                .width(contentWidth)
                .fillMaxHeight()
                .offset {
                    IntOffset(
                        swipeableState
                            .requireOffset()
                            .toInt(), 0
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = values[2],
                textAlign = TextAlign.Center,
                fontSize = fontSizeOne.em,
                color = fontColorOne,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun PreviewDefaultCarouselSelector() {
    val players = SampleRepository().getPlayerData()
    val playerNames = listOf<String>(
        players[0].name,
        players[1].name,
        players[2].name,
    )
    var currentSelected by remember {
        mutableIntStateOf(1)
    }
    DefaultCarouselSelector(
        values = playerNames,
        currentSelected = currentSelected
    ) {
        currentSelected = it
    }
}