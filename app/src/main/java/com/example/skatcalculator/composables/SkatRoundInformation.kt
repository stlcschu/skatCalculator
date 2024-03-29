package com.example.skatcalculator.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.skatcalculator.R
import com.example.skatcalculator.composables.defaults.DefaultCustomChip
import com.example.skatcalculator.database.tables.Player
import com.example.skatcalculator.database.tables.SkatRound
import com.example.skatcalculator.helper.SampleRepository
import com.example.skatcalculator.util.TextPainter

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkatRoundInformation(
    skatRound: SkatRound,
    player: Player,
    onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            val round = skatRound.roundIndex
            val gain = if (skatRound.pointsGainedPlayerOne > 0) skatRound.pointsGainedPlayerOne
            else if (skatRound.pointsGainedPlayerTwo > 0) skatRound.pointsGainedPlayerTwo
            else skatRound.pointsGainedPlayerThree
            val positiveGain = gain > 0
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(1f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${skatRound.roundVariant}",
                        modifier = Modifier.align(Alignment.CenterVertically),
                        color = TextPainter(TextPainter.Companion.PaintValue.HIGHLIGHT).getColor()
                    )
                    Text(text = "Round $round")
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close dialogue",
                        modifier = Modifier.clickable { onDismissRequest() }
                    )
                }
                Row {
                    Text(
                        text = player.name,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    if (positiveGain) Icon(painter = painterResource(id = R.drawable.baseline_keyboard_double_arrow_up_24), contentDescription = "Loss icon", tint = Color.Unspecified)
                    else Icon(painter = painterResource(id = R.drawable.baseline_keyboard_double_arrow_down_24), contentDescription = "Loss icon", tint = Color.Unspecified)
                    Text(
                        text = "${if (positiveGain) "+" else "-"}$gain",
                        modifier = Modifier.align(Alignment.CenterVertically),
                        color = TextPainter(if (positiveGain) TextPainter.Companion.PaintValue.GREEN else TextPainter.Companion.PaintValue.RED).getColor()
                    )
                }
                Row {
                    Icon(painter = painterResource(id = skatRound.trickColor.drawableId), contentDescription = "Trick icon", tint = Color.Unspecified)
                    Text(
                        text = skatRound.trickColor.value,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = skatRound.roundType.type,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Declarations:",
                        modifier = Modifier
                            .padding(end = 5.dp)
                    )
                    FlowRow {
                        skatRound.roundModifier.declarations.forEachIndexed { index, declaration ->
                            DefaultCustomChip {
                                Text(text = declaration.value)
                            }
                            if (index < skatRound.roundModifier.declarations.size) Spacer(modifier = Modifier.width(5.dp))
                        }
                        if (skatRound.isBockRound) Text(text = "Bock")
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Outcomes:",
                        modifier = Modifier
                            .padding(end = 5.dp)
                    )
                    FlowRow {
                        skatRound.roundModifier.roundOutcomes.forEachIndexed { index, outcome ->
                            DefaultCustomChip {
                                Text(text = outcome.value)
                            }
                            if (index < skatRound.roundModifier.declarations.size) Spacer(modifier = Modifier.width(5.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSkatRoundInformation() {
    val skatRound = SampleRepository().getSkatRound()
    SkatRoundInformation(
        skatRound = skatRound,
        player = Player("Hans"),
        onDismissRequest = {}
    )
}