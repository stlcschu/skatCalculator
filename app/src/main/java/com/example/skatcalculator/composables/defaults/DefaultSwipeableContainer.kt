package com.example.skatcalculator.composables.defaults

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.skatcalculator.R
import com.example.skatcalculator.helper.SampleRepository
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DefaultSwipeableContainer(
    item: T,
    animationDuration: Int = 500,
    dismissDirections: Set<DismissDirection>,
    isSelected: Boolean,
    onDelete: (T) -> Unit,
    onSelect: (T) -> Unit,
    onSelectChange: (Boolean) -> Unit,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember { mutableStateOf(false) }

    val state = rememberDismissState(
        confirmValueChange = { value ->
            when (value) {
                DismissValue.DismissedToStart -> {
                    isRemoved = true
                    true
                }
                DismissValue.DismissedToEnd -> {
                    onSelectChange(true)
                    true
                }
                else -> {
                    false
                }
            }
        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if(isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    LaunchedEffect(key1 = isSelected) {
        if(isSelected) {
            delay(animationDuration.toLong())
            onSelect(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismiss(
            state = state,
            background = {
                SwipeBackground(swipeDismissState = state)
            },
            dismissContent = { content(item) },
            directions = dismissDirections
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeBackground(
    swipeDismissState: DismissState
) {
    val color = when (swipeDismissState.dismissDirection) {
        DismissDirection.EndToStart -> colorResource(id = R.color.fire_engine_red)
        DismissDirection.StartToEnd -> colorResource(id = R.color.screamin_green)
        else -> Color.Transparent
    }
    
    val alignment = when (swipeDismissState.dismissDirection) {
        DismissDirection.EndToStart -> Alignment.CenterEnd
        else -> Alignment.CenterStart
    }
    
    val icon = when (swipeDismissState.dismissDirection) {
        DismissDirection.EndToStart -> Icons.Default.Delete
        else -> Icons.Default.Info
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
        contentAlignment = alignment
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White
        )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewDefaultSwipeableRow() {
    val skatRound = SampleRepository().getSkatRound()
    var isSelected by remember { mutableStateOf(false) }
    DefaultSwipeableContainer(
        item = skatRound,
        dismissDirections =  setOf(
            DismissDirection.EndToStart,
            DismissDirection.StartToEnd
        ),
        isSelected = isSelected,
        onDelete = {},
        onSelect = {
                   isSelected = false
        },
        onSelectChange = {
            isSelected = it
        }
    ) {
        Text(text = "HI")
    }
}