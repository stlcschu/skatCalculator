package com.example.skatcalculator.composables.defaults

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.skatcalculator.R

@Composable
fun DefaultCardSimple(
    header: String,
    modifier: Modifier =
        Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(start = 10.dp, end = 10.dp),
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = header,
            color = colorResource(id = R.color.gunmetal)
        )

        ElevatedCard(
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp)
                .fillMaxSize(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.anti_flash_white)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 5.dp, end = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                content()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultCardClickable(
    header: String,
    modifier: Modifier =
        Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(start = 10.dp, end = 10.dp),
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = header,
            color = colorResource(id = R.color.gunmetal)
        )

        ElevatedCard(
            onClick = onClick,
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp)
                .fillMaxSize(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.anti_flash_white)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 5.dp, end = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                content()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultCardClickableWithButton(
    header: String,
    buttonIcon: Int,
    modifier: Modifier =
        Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(start = 10.dp, end = 10.dp),
    onClick: () -> Unit,
    buttonFunction: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = header,
            color = colorResource(id = R.color.gunmetal)
        )

        ElevatedCard(
            onClick = onClick,
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp)
                .fillMaxSize(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.anti_flash_white)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 5.dp, end = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                content()
                IconButton(
                    onClick = buttonFunction
                ) {
                    Icon(painter = painterResource(id = buttonIcon), contentDescription = "Button icon")
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewDefaultCardSimple() {
    DefaultCardSimple(
        header = "Header",
    ) {
        Text(text = "fdfd")
    }
}

@Preview
@Composable
fun PreviewDefaultCardClickable() {
    DefaultCardClickable(
        header = "Header",
        onClick = {}
    ) {
        Text(text = "fdfd")
    }
}

@Preview
@Composable
fun PreviewDefaultCardClickableWithButton() {
    DefaultCardClickableWithButton(
        header = "Header",
        buttonIcon = R.drawable.baseline_remove_24,
        onClick = {},
        buttonFunction = {}
    ) {
        Text(text = "fdfd")
    }
}