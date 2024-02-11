package com.example.skatcalculator.composables.defaults

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DefaultCustomChip(
    content: @Composable () -> Unit
) {
    OutlinedCard(
        shape = RoundedCornerShape(5.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(2.dp)
        ) {
            content()
        }
    }
}

@Preview
@Composable
fun PreviewDefaultCustomChip() {
    DefaultCustomChip {

        Text(text = "Gamba")
    }
}