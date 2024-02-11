package com.example.skatcalculator.composables.defaults

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.skatcalculator.R
import com.example.skatcalculator.enums.RoundVariant

@Composable
fun DefaultTabs(
    currentRoundVariant: RoundVariant,
    tabs: List<RoundVariant>,
    onTabChange: (RoundVariant) -> Unit
) {

    val normalTabColor by animateColorAsState(
        targetValue = if (tabs[0] == currentRoundVariant) colorResource(id = R.color.Lavender_web)
        else colorResource(id = R.color.anti_flash_white),
        animationSpec = tween(
            500
        ),
        label = ""
    )
    val ramschTabColor by animateColorAsState(
        targetValue = if (tabs[1] == currentRoundVariant) colorResource(id = R.color.Lavender_web)
        else colorResource(id = R.color.anti_flash_white),
        animationSpec = tween(
            500
        ),
        label = ""
    )
    val grandTabColor by animateColorAsState(
        targetValue = if (tabs[2] == currentRoundVariant) colorResource(id = R.color.Lavender_web)
        else colorResource(id = R.color.anti_flash_white),
        animationSpec = tween(
            500
        ),
        label = ""
    )
    val nullspielTabColor by animateColorAsState(
        targetValue = if (tabs[3] == currentRoundVariant) colorResource(id = R.color.Lavender_web)
        else colorResource(id = R.color.anti_flash_white),
        animationSpec = tween(
            500
        ),
        label = ""
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Box(
            modifier = Modifier
                .width(65.dp)
                .fillMaxHeight()
                .clickable {
                    onTabChange(tabs[0])
                }
                .weight(1f)
                .background(
                    color = normalTabColor,
                    shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = tabs[0].value,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Clip,
                maxLines = 1
            )
        }
        Box(
            modifier = Modifier
                .width(65.dp)
                .fillMaxHeight()
                .clickable {
                    onTabChange(tabs[1])
                }
                .weight(1f)
                .background(
                    color = ramschTabColor,
                    shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = tabs[1].value,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Clip,
                maxLines = 1
            )
        }
        Box(
            modifier = Modifier
                .width(65.dp)
                .fillMaxHeight()
                .clickable {
                    onTabChange(tabs[2])
                }
                .weight(1f)
                .background(
                    color = grandTabColor,
                    shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = tabs[2].value,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Clip,
                maxLines = 1
            )
        }
        Box(
            modifier = Modifier
                .width(65.dp)
                .fillMaxHeight()
                .clickable {
                    onTabChange(tabs[3])
                }
                .weight(1f)
                .background(
                    color = nullspielTabColor,
                    shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = tabs[3].value,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Clip,
                maxLines = 1
            )
        }
    }

}

@Preview
@Composable
fun PreviewDefaultTabs() {
    var roundVariant by remember {
        mutableStateOf(RoundVariant.RAMSCH)
    }
    DefaultTabs(
        currentRoundVariant = roundVariant,
        listOf(
            RoundVariant.NORMAL,
            RoundVariant.RAMSCH,
            RoundVariant.GRAND,
            RoundVariant.NULLSPIEL
        ),
        onTabChange = {
            roundVariant = it
        }
    )
}