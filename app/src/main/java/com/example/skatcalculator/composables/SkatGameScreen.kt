package com.example.skatcalculator.composables

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.skatcalculator.R
import com.example.skatcalculator.database.events.SkatGameEvent
import com.example.skatcalculator.database.events.SkatRoundEvent
import com.example.skatcalculator.database.tables.Player
import com.example.skatcalculator.database.tables.Score
import com.example.skatcalculator.database.tables.SkatGameWithRoundsAndScores
import com.example.skatcalculator.database.tables.SkatRound
import com.example.skatcalculator.database.tables.SpecialRounds
import com.example.skatcalculator.dataclasses.PlayerWithScore
import com.example.skatcalculator.dataclasses.RoundModifier
import com.example.skatcalculator.enums.Declaration
import com.example.skatcalculator.enums.RoundOutcome
import com.example.skatcalculator.enums.RoundVariant
import com.example.skatcalculator.enums.RoundType
import com.example.skatcalculator.enums.SkatScreen
import com.example.skatcalculator.enums.SpecialRound
import com.example.skatcalculator.enums.TrickColor
import com.example.skatcalculator.helper.SampleRepository
import com.example.skatcalculator.states.SkatRoundInformationState
import com.example.skatcalculator.states.SkatRoundState
import com.example.skatcalculator.util.GroupPlayerWithScore
import com.example.skatcalculator.util.ScoreCalculator
import com.example.skatcalculator.util.TextPainter
import com.example.skatcalculator.util.TextPainter.Companion.PaintValue
import kotlin.math.roundToInt

@Composable
fun SkatGameScreen(
    skatGame: SkatGameWithRoundsAndScores,
    skatRoundState: SkatRoundState,
    specialRoundsState: SpecialRounds,
    roundInformationState: SkatRoundInformationState,
    onSkatRoundEvent: (SkatRoundEvent) -> Unit,
    onSkatGameEvent: (SkatGameEvent) -> Unit
) {
    val rounds = skatGame.rounds
    val players = GroupPlayerWithScore(skatGame.skatGame.getPlayers(), skatGame.scores).group()
    val currentRound = rounds.size + 1
    val specialRounds = specialRoundsState.specialRounds
    var showBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (players.isEmpty()) {
            LinearProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
            return
        }
        var currentContent by remember { mutableStateOf(SkatScreen.GAME_SCREEN) }
        Header(
            selectedScreen = currentContent,
            onHeaderClicked = { screen ->
                currentContent = screen
            }
        )
        when(currentContent) {
            SkatScreen.GAME_SCREEN -> {
                MainGameScreen(
                    players,
                    showBottomSheet = showBottomSheet,
                    roundState = skatRoundState,
                    currentRound = currentRound,
                    gameId = skatGame.skatGame.skatGameId,
                    specialRounds = specialRounds,
                    onSkatRoundEvent = onSkatRoundEvent,
                    onSkatGameEvent = onSkatGameEvent,
                    onShowBottomSheetChanged = {
                        showBottomSheet = it
                    }
                )
            }
            SkatScreen.BID_TABLE -> {
                SkatTable()
            }
            SkatScreen.ROUND_HISTORY -> {
                HistoryGameScreen(
                    players,
                    rounds,
                    roundInformationState,
                    onRoundStateChange = { round,player ->
                        onSkatRoundEvent(
                            SkatRoundEvent.onUpdateRoundInformationState(round, player)
                        )
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainGameScreen(
    players: List<PlayerWithScore>,
    showBottomSheet: Boolean,
    roundState: SkatRoundState,
    currentRound: Int,
    gameId: String,
    specialRounds: List<SpecialRound>,
    onSkatRoundEvent: (SkatRoundEvent) -> Unit,
    onSkatGameEvent: (SkatGameEvent) -> Unit,
    onShowBottomSheetChanged: (Boolean) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(false)
    val shoveList = listOf<Int>(0, 1, 2, 3)

    val currentSpecialRound = if (specialRounds.isEmpty()) SpecialRound.NONE else specialRounds[0]

    val focusRequester = remember { FocusRequester() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        val (giver, bidder) = decideCardGiverAndFirstBidder(currentRound)
        PlayerBox(
            player = players[0],
            isFirstBidder = giver == 0,
            isCardGiver = bidder == 0,
            height = 75.dp
        )
        PlayerBox(
            player = players[1],
            isFirstBidder = giver == 1,
            isCardGiver = bidder == 1,
            height = 75.dp
        )
        PlayerBox(
            player = players[2],
            isFirstBidder = giver == 2,
            isCardGiver = bidder == 2,
            height = 75.dp
        )
    }

    Text(
        text = currentSpecialRound.displayValue,
        color = TextPainter(PaintValue.HIGHLIGHT).getColor(),
        fontSize = 4.em
    )

    if (currentSpecialRound == SpecialRound.RAMSCH) {
        ExposedDropdownMenuBox(
            expanded = roundState.shoveDropdownExpanded,
            onExpandedChange = {
                onSkatRoundEvent(
                    SkatRoundEvent.onShoveDropdownExpandedChanged(
                        !roundState.shoveDropdownExpanded
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            TextField(
                readOnly = true,
                value = roundState.selectedShove.toString(),
                onValueChange = {},
                label = { Text("Shoves") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = roundState.shoveDropdownExpanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = roundState.shoveDropdownExpanded,
                onDismissRequest = {
                    onSkatRoundEvent(
                        SkatRoundEvent.onShoveDropdownExpandedChanged(
                            false
                        )
                    )
                },
                modifier = Modifier.focusRequester(focusRequester)
            ) {
                shoveList.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            onSkatRoundEvent(
                                SkatRoundEvent.onSelectedShoveChanged(
                                    selectionOption
                                )
                            )
                            onSkatRoundEvent(
                                SkatRoundEvent.onShoveDropdownExpandedChanged(
                                    false
                                )
                            )
                        },
                        text = { Text(text = selectionOption.toString()) }
                    )
                }
            }
        }
    }
    else {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val selected = roundState.roundVariant == RoundVariant.NORMAL
                RadioButton(
                    selected = selected,
                    onClick = {
                        onSkatRoundEvent(
                            SkatRoundEvent.onRoundVariantChanged(RoundVariant.NORMAL)
                        )
                        onSkatRoundEvent(
                            SkatRoundEvent.onResetNormalVariant
                        )
                    }
                )
                Text(
                    text = RoundVariant.NORMAL.value,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val selected = roundState.roundVariant == RoundVariant.GRAND
                RadioButton(
                    selected = selected,
                    onClick = {
                        onSkatRoundEvent(
                            SkatRoundEvent.onRoundVariantChanged(RoundVariant.GRAND)
                        )
                        onSkatRoundEvent(
                            SkatRoundEvent.onResetGrandVariant
                        )
                    }
                )
                Text(
                    text = RoundVariant.GRAND.value,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                )
            }
            Column {
                val selected = roundState.roundVariant == RoundVariant.NULLSPIEL
                RadioButton(
                    selected = selected,
                    onClick = {
                        onSkatRoundEvent(
                            SkatRoundEvent.onRoundVariantChanged(RoundVariant.NULLSPIEL)
                        )
                        onSkatRoundEvent(
                            SkatRoundEvent.onResetNullSpielVariant
                        )
                    }
                )
                Text(
                    text = RoundVariant.NULLSPIEL.value,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                )
            }
            Column {
                val selected = roundState.roundVariant == RoundVariant.RAMSCH
                RadioButton(
                    selected = selected,
                    onClick = {
                        onSkatRoundEvent(
                            SkatRoundEvent.onRoundVariantChanged(RoundVariant.RAMSCH)
                        )
                        onSkatRoundEvent(
                            SkatRoundEvent.onResetRamschVariant
                        )
                    }
                )
                Text(
                    text = RoundVariant.RAMSCH.value,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
        when(roundState.roundVariant) {
            RoundVariant.NULLSPIEL -> {
                ExposedDropdownMenuBox(
                    expanded = roundState.playerDropdownExpanded,
                    onExpandedChange = {
                        onSkatRoundEvent(
                            SkatRoundEvent.onPlayerDropdownExpandedChanged(!roundState.playerDropdownExpanded)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    TextField(
                        readOnly = true,
                        value = roundState.selectedPlayer.getPlayerName(),
                        onValueChange = {},
                        label = { Text("Player") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = roundState.playerDropdownExpanded
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        shape = RoundedCornerShape(0.dp),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = roundState.playerDropdownExpanded,
                        onDismissRequest = {
                            onSkatRoundEvent(
                                SkatRoundEvent.onPlayerDropdownExpandedChanged(false)
                            )
                        },
                        modifier = Modifier.focusRequester(focusRequester)
                    ) {
                        players.forEach { selectionOption ->
                            DropdownMenuItem(
                                onClick = {
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onSelectedPlayerChanged(
                                            selectionOption
                                        )
                                    )
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onPlayerDropdownExpandedChanged(false)
                                    )
                                },
                                text = { Text(text = selectionOption.getPlayerName()) }
                            )
                        }
                    }
                }
                val checkBoxWidth = 110.dp
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .width(checkBoxWidth)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = roundState.reChecked,
                                onCheckedChange = { isChecked ->
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onReCheckedChanged(isChecked)
                                    )
                                }
                            )
                            Text(text = Declaration.RE.value)
                        }
                    }
                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .width(checkBoxWidth)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = roundState.ouverChecked,
                                onCheckedChange = { isChecked ->
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onOuverCheckedChanged(isChecked)
                                    )
                                }
                            )
                            Text(text = Declaration.OUVER.value)
                        }
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .width(checkBoxWidth)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = roundState.kontraChecked,
                                onCheckedChange = { isChecked ->
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onKontraCheckedChanged(isChecked)
                                    )
                                }
                            )
                            Text(text = Declaration.KONTRA.value)
                        }
                    }

                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .width(checkBoxWidth)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = roundState.handChecked,
                                onCheckedChange = { isChecked ->
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onHandCheckedChanged(isChecked)
                                    )
                                }
                            )
                            Text(text = Declaration.HAND.value)
                        }
                    }
                }
            }
            RoundVariant.RAMSCH -> {
                ExposedDropdownMenuBox(
                    expanded = roundState.shoveDropdownExpanded,
                    onExpandedChange = {
                        onSkatRoundEvent(
                            SkatRoundEvent.onShoveDropdownExpandedChanged(
                                !roundState.shoveDropdownExpanded
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    TextField(
                        readOnly = true,
                        value = roundState.selectedShove.toString(),
                        onValueChange = {},
                        label = { Text("Shoves") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = roundState.shoveDropdownExpanded
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        shape = RoundedCornerShape(0.dp),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = roundState.shoveDropdownExpanded,
                        onDismissRequest = {
                            onSkatRoundEvent(
                                SkatRoundEvent.onShoveDropdownExpandedChanged(
                                    false
                                )
                            )
                        },
                        modifier = Modifier.focusRequester(focusRequester)
                    ) {
                        shoveList.forEach { selectionOption ->
                            DropdownMenuItem(
                                onClick = {
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onSelectedShoveChanged(
                                            selectionOption
                                        )
                                    )
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onShoveDropdownExpandedChanged(
                                            false
                                        )
                                    )
                                },
                                text = { Text(text = selectionOption.toString()) }
                            )
                        }
                    }
                }
            }
            else -> {
                ExposedDropdownMenuBox(
                    expanded = roundState.playerDropdownExpanded,
                    onExpandedChange = {
                        onSkatRoundEvent(
                            SkatRoundEvent.onPlayerDropdownExpandedChanged(!roundState.playerDropdownExpanded)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    TextField(
                        readOnly = true,
                        value = roundState.selectedPlayer.getPlayerName(),
                        onValueChange = {},
                        label = { Text("Player") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = roundState.playerDropdownExpanded
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        shape = RoundedCornerShape(0.dp),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = roundState.playerDropdownExpanded,
                        onDismissRequest = {
                            onSkatRoundEvent(
                                SkatRoundEvent.onPlayerDropdownExpandedChanged(false)
                            )
                        },
                        modifier = Modifier.focusRequester(focusRequester)
                    ) {
                        players.forEach { selectionOption ->
                            DropdownMenuItem(
                                onClick = {
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onSelectedPlayerChanged(
                                            selectionOption
                                        )
                                    )
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onPlayerDropdownExpandedChanged(false)
                                    )
                                },
                                text = { Text(text = selectionOption.getPlayerName()) }
                            )
                        }
                    }
                }
                if (roundState.roundVariant == RoundVariant.NORMAL) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Absolute.SpaceEvenly
                    ) {
                        Icon(
                            painter = painterResource(id = if(roundState.selectedTrick == TrickColor.CROSSES) R.drawable.symbol_cross else R.drawable.symbol_cross_hollow),
                            contentDescription = "Crosses selection button",
                            modifier = Modifier
                                .clickable {
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onSelectedTrickChanged(
                                            TrickColor.CROSSES
                                        )
                                    )
                                }
                                .size(50.dp),
                            tint = Color.Unspecified
                        )
                        Icon(
                            painter = painterResource(id = if(roundState.selectedTrick == TrickColor.SPADES) R.drawable.symbol_spade else R.drawable.symbol_spade_hollow),
                            contentDescription = "Crosses selection button",
                            modifier = Modifier
                                .clickable {
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onSelectedTrickChanged(
                                            TrickColor.SPADES
                                        )
                                    )
                                }
                                .size(50.dp),
                            tint = Color.Unspecified
                        )
                        Icon(
                            painter = painterResource(id = if(roundState.selectedTrick == TrickColor.HEARTS) R.drawable.symbol_heart else R.drawable.symbol_heart_hollow),
                            contentDescription = "Crosses selection button",
                            modifier = Modifier
                                .clickable {
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onSelectedTrickChanged(
                                            TrickColor.HEARTS
                                        )
                                    )
                                }
                                .size(50.dp),
                            tint = Color.Unspecified
                        )
                        Icon(
                            painter = painterResource(id = if(roundState.selectedTrick == TrickColor.DIAMONDS) R.drawable.symbol_diamond else R.drawable.symbol_diamond_hollow),
                            contentDescription = "Crosses selection button",
                            modifier = Modifier
                                .clickable {
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onSelectedTrickChanged(
                                            TrickColor.DIAMONDS
                                        )
                                    )
                                }
                                .size(50.dp),
                            tint = Color.Unspecified
                        )
                    }
                }
                val checkBoxWidth = 110.dp
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .width(checkBoxWidth)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = roundState.reChecked,
                                onCheckedChange = { isChecked ->
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onReCheckedChanged(isChecked)
                                    )
                                }
                            )
                            Text(text = Declaration.RE.value)
                        }
                    }
                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .width(checkBoxWidth)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = roundState.ouverChecked,
                                onCheckedChange = { isChecked ->
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onOuverCheckedChanged(isChecked)
                                    )
                                }
                            )
                            Text(text = Declaration.OUVER.value)
                        }
                    }
                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .width(checkBoxWidth)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = roundState.schneiderChecked,
                                onCheckedChange = { isChecked ->
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onSchneiderCheckedChanged(isChecked)
                                    )
                                    if (!isChecked) {
                                        onSkatRoundEvent(
                                            SkatRoundEvent.onSchwarzCheckedChanged(false)
                                        )
                                    }
                                }
                            )
                            Text(
                                text = Declaration.SCHNEIDER.value
                            )
                        }
                    }

                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .width(checkBoxWidth)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = roundState.kontraChecked,
                                onCheckedChange = { isChecked ->
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onKontraCheckedChanged(isChecked)
                                    )
                                }
                            )
                            Text(text = Declaration.KONTRA.value)
                        }
                    }

                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .width(checkBoxWidth)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = roundState.handChecked,
                                onCheckedChange = { isChecked ->
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onHandCheckedChanged(isChecked)
                                    )
                                }
                            )
                            Text(text = Declaration.HAND.value)
                        }
                    }

                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .width(checkBoxWidth)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = roundState.schwarzChecked,
                                onCheckedChange = { isChecked ->
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onSchwarzCheckedChanged(isChecked)
                                    )
                                },
                                enabled = roundState.schneiderChecked
                            )
                            Text(
                                text = Declaration.SCHWARZ.value
                            )
                        }
                    }
                }
            }
        }
    }


    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.SpaceAround
    ) {
        val calculatedGainLoss = ScoreCalculator()
            .calculateDisplayScore(
                skatRoundState = roundState,
                isBockRound = currentSpecialRound == SpecialRound.BOCK
            )
        Box(
            modifier = Modifier
                .width(140.dp)
                .height(50.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_trending_up_24),
                    contentDescription = "Score trend icon",
                    modifier = Modifier
                        .size(40.dp),
                    tint = TextPainter(PaintValue.GREEN).getColor()
                )
                Column() {
                    Text(
                        text = "Potential gain",
                        fontSize = 3.5.em,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "${calculatedGainLoss.first}",
                        fontSize = 3.em,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = if (calculatedGainLoss.second < 0) TextPainter(PaintValue.GREEN).getColor()
                        else TextPainter(PaintValue.NONE).getColor()
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .width(140.dp)
                .height(50.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_trending_down_24),
                    contentDescription = "Score trend icon",
                    modifier = Modifier
                        .size(40.dp),
                    tint = TextPainter(PaintValue.RED).getColor()
                )
                Column() {
                    Text(
                        text = "Potential loss",
                        fontSize = 3.5.em,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "${calculatedGainLoss.second}",
                        fontSize = 3.em,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = if (calculatedGainLoss.second < 0) TextPainter(PaintValue.RED).getColor()
                        else TextPainter(PaintValue.NONE).getColor()
                    )
                }
            }
        }
    }
    val context = LocalContext.current
    Button(
        onClick = {
            if (roundState.selectedPlayer.player.isEmpty() && roundState.roundVariant != RoundVariant.RAMSCH) {
                Toast.makeText(context, "No player selected", Toast.LENGTH_SHORT).show()
                return@Button
            }
            onShowBottomSheetChanged(true)
        },
        modifier = Modifier
            .fillMaxWidth(0.9f)
    ) {
        Text(text = "End round")
    }
    if (showBottomSheet) {

        ModalBottomSheet(
            onDismissRequest = {
                onShowBottomSheetChanged(false)
            },
            modifier = Modifier.height(500.dp),
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "End of Round",
                    fontSize = 6.em
                )
                when (roundState.roundVariant) {
                    RoundVariant.RAMSCH -> {
                        ExposedDropdownMenuBox(
                            expanded = roundState.playerDropdownExpanded,
                            onExpandedChange = {
                                onSkatRoundEvent(
                                    SkatRoundEvent.onPlayerDropdownExpandedChanged(!roundState.playerDropdownExpanded)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            TextField(
                                readOnly = true,
                                value = roundState.selectedPlayer.getPlayerName(),
                                onValueChange = {},
                                label = { Text("Player") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = roundState.playerDropdownExpanded
                                    )
                                },
                                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                shape = RoundedCornerShape(0.dp),
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                            )
                            ExposedDropdownMenu(
                                expanded = roundState.playerDropdownExpanded,
                                onDismissRequest = {
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onPlayerDropdownExpandedChanged(false)
                                    )
                                },
                                modifier = Modifier.focusRequester(focusRequester)
                            ) {
                                players.forEach { selectionOption ->
                                    DropdownMenuItem(
                                        onClick = {
                                            onSkatRoundEvent(
                                                SkatRoundEvent.onSelectedPlayerChanged(
                                                    selectionOption
                                                )
                                            )
                                            onSkatRoundEvent(
                                                SkatRoundEvent.onPlayerDropdownExpandedChanged(false)
                                            )
                                        },
                                        text = { Text(text = selectionOption.getPlayerName()) }
                                    )
                                }
                            }
                        }
                        if (!roundState.selectedPlayer.player.isEmpty()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Column(
                                    modifier = Modifier
                                        .clickable {
                                            onSkatRoundEvent(
                                                SkatRoundEvent.onRoundScoreValueChanged(if (roundState.roundScore - 1f < 0) roundState.roundScore else roundState.roundScore - 1f)
                                            )
                                        }
                                        .width(60.dp)
                                ) {
                                    Text(
                                        text = roundState.selectedPlayer.getPlayerName(),
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = roundState.roundScore.roundToInt().toString(),
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        textAlign = TextAlign.Right
                                    )
                                }
                                Slider(
                                    value = roundState.roundScore,
                                    onValueChange = {
                                        onSkatRoundEvent(
                                            SkatRoundEvent.onRoundScoreValueChanged(it)
                                        )
                                        onSkatRoundEvent(
                                            SkatRoundEvent.onIsSpaltarschChanged(
                                                roundState.roundScore.roundToInt() == 60
                                            )
                                        )
                                        onSkatRoundEvent(
                                            SkatRoundEvent.onSuccessfulSchneiderChanged(
                                                if (roundState.schneiderChecked) roundState.roundScore.roundToInt() > 90
                                                else false
                                            )
                                        )
                                    },
                                    valueRange = 0f..120f,
                                    steps = 119,
                                    modifier = Modifier
                                        .fillMaxWidth(0.75f)
                                        .padding(end = 5.dp, start = 5.dp)
                                )
                                Column(
                                    modifier = Modifier
                                        .clickable {
                                            onSkatRoundEvent(
                                                SkatRoundEvent.onRoundScoreValueChanged(if (roundState.roundScore + 1f > 120) 120f else roundState.roundScore + 1f)
                                            )
                                        }
                                        .width(60.dp)
                                ) {
                                    Text(
                                        text = "Others",
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                    )
                                    Text(
                                        text = (120 - roundState.roundScore.roundToInt()).toString(),
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        textAlign = TextAlign.Left
                                    )
                                }
                            }

                            val checkBoxWidth = 140.dp
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .height(50.dp)
                                        .width(checkBoxWidth)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Checkbox(
                                            checked = roundState.jungfrauChecked,
                                            onCheckedChange = { isChecked ->
                                                onSkatRoundEvent(
                                                    SkatRoundEvent.onJungfrauCheckedChanged(if (roundState.successfulDurchmarschChecked) true else isChecked)
                                                )
                                            }
                                        )
                                        Text(text = "Jungfrau")
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .height(50.dp)
                                        .width(checkBoxWidth)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Checkbox(
                                            checked = roundState.successfulDurchmarschChecked,
                                            onCheckedChange = { isChecked ->
                                                onSkatRoundEvent(
                                                    SkatRoundEvent.onSuccessfulDurchmarschCheckedChanged(
                                                        isChecked
                                                    )
                                                )
                                                onSkatRoundEvent(
                                                    SkatRoundEvent.onJungfrauCheckedChanged(isChecked)
                                                )
                                            },
                                            enabled = roundState.roundScore.roundToInt() == 120
                                        )
                                        Text(text = "Successful Durchmarsch")
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 10.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                val playerScore = roundState.selectedPlayer.score.score
                                val calculatedGainLoss = ScoreCalculator()
                                    .calculatePotentialSingleScore(
                                        skatRoundState = roundState,
                                        isBockRound = currentSpecialRound == SpecialRound.BOCK
                                    )
                                Box(
                                    modifier = Modifier
                                        .width(300.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        val color = if (calculatedGainLoss > 0) TextPainter(PaintValue.GREEN).getColor() else TextPainter(PaintValue.RED).getColor()
                                        Column(
                                            modifier = Modifier
                                                .width(100.dp)
                                        ) {
                                            Text(
                                                text = "Current Score",
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                textAlign = TextAlign.Center,
                                                fontSize = 3.5.em
                                            )
                                            Text(
                                                text = "$playerScore",
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                textAlign = TextAlign.Center,
                                                fontSize = 3.5.em
                                            )
                                        }
                                        Column(
                                            modifier = Modifier
                                                .width(70.dp)
                                                .height(70.dp)
                                                .padding(start = 10.dp, end = 10.dp)
                                        ) {
                                            val iconId =
                                                if (calculatedGainLoss > 0) R.drawable.baseline_trending_up_24
                                                else R.drawable.baseline_trending_down_24
                                            Icon(
                                                painter = painterResource(id = iconId),
                                                contentDescription = "Score trend icon",
                                                tint = color,
                                                modifier = Modifier
                                                    .size(50.dp)
                                            )
                                            Text(
                                                text = "$calculatedGainLoss",
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                textAlign = TextAlign.Center,
                                                color = color,
                                                fontSize = 3.5.em
                                            )
                                        }
                                        Column(
                                            modifier = Modifier
                                                .width(100.dp)
                                        ) {
                                            Text(
                                                text = "After Round",
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                textAlign = TextAlign.Center,
                                                fontSize = 3.5.em
                                            )
                                            val newScore = playerScore + calculatedGainLoss
                                            val newScoreColor = if(newScore > 0) TextPainter(PaintValue.GREEN).getColor()
                                            else if(newScore < 0) TextPainter(PaintValue.RED).getColor()
                                            else TextPainter(PaintValue.NONE).getColor()
                                            Text(
                                                text = "$newScore",
                                                color = newScoreColor,
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                textAlign = TextAlign.Center,
                                                fontSize = 3.5.em
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    RoundVariant.NULLSPIEL -> {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(250.dp)
                                    .height(50.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Switch(
                                        checked = roundState.successfulNullSpielChecked,
                                        onCheckedChange = {
                                            onSkatRoundEvent(
                                                SkatRoundEvent.onNullSpielCheckedChanged(it)
                                            )
                                        }
                                    )
                                    Spacer(
                                        modifier = Modifier
                                            .width(10.dp)
                                    )
                                    Text(
                                        text = if (roundState.successfulNullSpielChecked) "Nullspiel successful" else "Nullspiel failed",
                                        fontSize = 4.5.em
                                    )
                                }
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            val playerScore = roundState.selectedPlayer.score.score
                            val calculatedGainLoss = ScoreCalculator()
                                .calculatePotentialSingleScore(
                                    skatRoundState = roundState,
                                    isBockRound = currentSpecialRound == SpecialRound.BOCK
                                )
                            Box(
                                modifier = Modifier
                                    .width(300.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    val color = if (calculatedGainLoss > 0) TextPainter(PaintValue.GREEN).getColor() else TextPainter(PaintValue.RED).getColor()
                                    Column(
                                        modifier = Modifier
                                            .width(100.dp)
                                    ) {
                                        Text(
                                            text = "Current Score",
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            textAlign = TextAlign.Center,
                                            fontSize = 3.5.em
                                        )
                                        Text(
                                            text = "$playerScore",
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            textAlign = TextAlign.Center,
                                            fontSize = 3.5.em
                                        )
                                    }
                                    Column(
                                        modifier = Modifier
                                            .width(70.dp)
                                            .height(70.dp)
                                            .padding(start = 10.dp, end = 10.dp)
                                    ) {
                                        val iconId =
                                            if (calculatedGainLoss > 0) R.drawable.baseline_trending_up_24
                                            else R.drawable.baseline_trending_down_24
                                        Icon(
                                            painter = painterResource(id = iconId),
                                            contentDescription = "Score trend icon",
                                            tint = color,
                                            modifier = Modifier
                                                .size(50.dp)
                                        )
                                        Text(
                                            text = "$calculatedGainLoss",
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            textAlign = TextAlign.Center,
                                            color = color,
                                            fontSize = 3.5.em
                                        )
                                    }
                                    Column(
                                        modifier = Modifier
                                            .width(100.dp)
                                    ) {
                                        Text(
                                            text = "After Round",
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            textAlign = TextAlign.Center,
                                            fontSize = 3.5.em
                                        )
                                        val newScore = playerScore + calculatedGainLoss
                                        val newScoreColor = if(newScore > 0) TextPainter(PaintValue.GREEN).getColor()
                                        else if(newScore < 0) TextPainter(PaintValue.RED).getColor()
                                        else TextPainter(PaintValue.NONE).getColor()
                                        Text(
                                            text = "$newScore",
                                            color = newScoreColor,
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            textAlign = TextAlign.Center,
                                            fontSize = 3.5.em
                                        )
                                    }
                                }
                            }
                        }
                    }
                    else -> {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column(
                                modifier = Modifier
                                    .clickable {
                                        onSkatRoundEvent(
                                            SkatRoundEvent.onRoundScoreValueChanged(if (roundState.roundScore - 1f < 0) roundState.roundScore else roundState.roundScore - 1f)
                                        )
                                    }
                                    .width(60.dp)
                            ) {
                                Text(
                                    text = roundState.selectedPlayer.getPlayerName(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = roundState.roundScore.roundToInt().toString(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Right
                                )
                            }
                            Slider(
                                value = roundState.roundScore,
                                onValueChange = {
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onRoundScoreValueChanged(it)
                                    )
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onIsSpaltarschChanged(
                                            roundState.roundScore.roundToInt() == 60
                                        )
                                    )
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onSuccessfulSchneiderChanged(
                                            if (roundState.schneiderChecked) roundState.roundScore.roundToInt() > 90
                                            else false
                                        )
                                    )
                                },
                                valueRange = 0f..120f,
                                steps = 119,
                                modifier = Modifier
                                    .fillMaxWidth(0.75f)
                                    .padding(end = 5.dp, start = 5.dp)
                            )
                            Column(
                                modifier = Modifier
                                    .clickable {
                                        onSkatRoundEvent(
                                            SkatRoundEvent.onRoundScoreValueChanged(if (roundState.roundScore + 1f > 120) 120f else roundState.roundScore + 1f)
                                        )
                                    }
                                    .width(60.dp)
                            ) {
                                Text(
                                    text = "Others",
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                )
                                Text(
                                    text = (120 - roundState.roundScore.roundToInt()).toString(),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Left
                                )
                            }
                        }
                        ExposedDropdownMenuBox(
                            expanded = roundState.roundTypeDropDownExpanded,
                            onExpandedChange = {
                                onSkatRoundEvent(
                                    SkatRoundEvent.onRoundTypeDropDownExpandedChanged(
                                        !roundState.roundTypeDropDownExpanded
                                    )
                                )
                            }
                        ) {
                            TextField(
                                readOnly = true,
                                value = roundState.selectedRoundType.type,
                                onValueChange = {},
                                label = { Text("Jacks") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = roundState.roundTypeDropDownExpanded
                                    )
                                },
                                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                shape = RoundedCornerShape(0.dp),
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                            )
                            ExposedDropdownMenu(
                                expanded = roundState.roundTypeDropDownExpanded,
                                onDismissRequest = {
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onRoundTypeDropDownExpandedChanged(
                                            false
                                        )
                                    )
                                },
                                modifier = Modifier.focusRequester(focusRequester)
                            ) {
                                RoundType.toList().forEach { selectionOption ->
                                    DropdownMenuItem(
                                        onClick = {
                                            onSkatRoundEvent(
                                                SkatRoundEvent.onSelectedRoundTypeChanged(
                                                    RoundType.fromString(selectionOption.type)
                                                )
                                            )
                                            onSkatRoundEvent(
                                                SkatRoundEvent.onRoundTypeDropDownExpandedChanged(
                                                    false
                                                )
                                            )
                                        },
                                        text = { Text(text = selectionOption.type) }
                                    )
                                }
                            }
                        }
                        if (roundState.schwarzChecked) {
                            val checkBoxWidth = 175.dp
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .height(50.dp)
                                        .width(checkBoxWidth)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Checkbox(
                                            checked = roundState.successfulSchwarz,
                                            onCheckedChange = { isChecked ->
                                                onSkatRoundEvent(
                                                    SkatRoundEvent.onSuccessfulSchwarzCheckedChanged(isChecked)
                                                )
                                            },
                                            enabled = roundState.roundScore.roundToInt() == 120
                                        )
                                        Text(
                                            text = "Successful Schwarz"
                                        )
                                    }
                                }
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            val playerScore = roundState.selectedPlayer.score.score
                            val calculatedGainLoss = ScoreCalculator()
                                .calculatePotentialSingleScore(
                                    skatRoundState = roundState,
                                    isBockRound = currentSpecialRound == SpecialRound.BOCK
                                )
                            Box(
                                modifier = Modifier
                                    .width(300.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    val color = if (calculatedGainLoss > 0) TextPainter(PaintValue.GREEN).getColor() else TextPainter(PaintValue.RED).getColor()
                                    Column(
                                        modifier = Modifier
                                            .width(100.dp)
                                    ) {
                                        Text(
                                            text = "Current Score",
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            textAlign = TextAlign.Center,
                                            fontSize = 3.5.em
                                        )
                                        Text(
                                            text = "$playerScore",
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            textAlign = TextAlign.Center,
                                            fontSize = 3.5.em
                                        )
                                    }
                                    Column(
                                        modifier = Modifier
                                            .width(70.dp)
                                            .height(70.dp)
                                            .padding(start = 10.dp, end = 10.dp)
                                    ) {
                                        val iconId =
                                            if (calculatedGainLoss > 0) R.drawable.baseline_trending_up_24
                                            else R.drawable.baseline_trending_down_24
                                        Icon(
                                            painter = painterResource(id = iconId),
                                            contentDescription = "Score trend icon",
                                            tint = color,
                                            modifier = Modifier
                                                .size(50.dp)
                                        )
                                        Text(
                                            text = "$calculatedGainLoss",
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            textAlign = TextAlign.Center,
                                            color = color,
                                            fontSize = 3.5.em
                                        )
                                    }
                                    Column(
                                        modifier = Modifier
                                            .width(100.dp)
                                    ) {
                                        Text(
                                            text = "After Round",
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            textAlign = TextAlign.Center,
                                            fontSize = 3.5.em
                                        )
                                        val newScore = playerScore + calculatedGainLoss
                                        val newScoreColor = if(newScore > 0) TextPainter(PaintValue.GREEN).getColor()
                                        else if(newScore < 0) TextPainter(PaintValue.RED).getColor()
                                        else TextPainter(PaintValue.NONE).getColor()
                                        Text(
                                            text = "$newScore",
                                            color = newScoreColor,
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            textAlign = TextAlign.Center,
                                            fontSize = 3.5.em
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                Button(
                    onClick = {
                        if (roundState.selectedPlayer.player.isEmpty() && roundState.roundVariant != RoundVariant.RAMSCH) {
                            Toast.makeText(context, "No player selected", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        onShowBottomSheetChanged(false)
                        val scoreAndSpecialRounds =
                            ScoreCalculator().calculateFinalScoreWithSpecialRounds(
                                roundState,
                                currentSpecialRound == SpecialRound.BOCK
                            )
                        val skatRound = constructSkatRound(
                            roundState = roundState,
                            isBockRound = currentSpecialRound == SpecialRound.BOCK,
                            gameId = gameId,
                            roundIndex = currentRound,
                            roundScore = scoreAndSpecialRounds.first,
                            players = players
                        )
                        onSkatRoundEvent(
                            SkatRoundEvent.addRound(
                                skatRound
                            )
                        )
                        val newScore =
                            roundState.selectedPlayer.score.score + scoreAndSpecialRounds.first
                        //                val newScores = roundState.selectedPlayer.score.scores.toMutableList()
                        //                newScores.add(newScore)
                        val scorePrime = roundState.selectedPlayer.score.copy(
                            score = newScore,
                            //                    scores = newScores
                        )



                        onSkatGameEvent(
                            SkatGameEvent.saveScore(
                                scorePrime
                            )
                        )
                        val newSpecialRounds =
                            addSpecialRounds(specialRounds, scoreAndSpecialRounds.second)
                        onSkatGameEvent(
                            SkatGameEvent.saveSpecialRounds(
                                SpecialRounds(
                                    specialRounds = newSpecialRounds,
                                    gameId = gameId
                                )
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                ) {
                    Text(text = "End Round")
                }
            }
        }
    }
}

@Composable
fun PlayerBox(
    player: PlayerWithScore,
    isFirstBidder: Boolean,
    isCardGiver: Boolean,
    height: Dp
) {
    Box(
        modifier = Modifier
            .height(height)
            .width(height)
    ) {
        Card(
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth(0.95f),
            colors = CardDefaults.cardColors(
                containerColor = Color.Unspecified
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = player.getPlayerName(),
                    fontSize = 5.em,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                val textColor = if (player.getScore() > 0) TextPainter(PaintValue.GREEN).getColor()
                else if(player.getScore() < 0) TextPainter(PaintValue.RED).getColor()
                else TextPainter(PaintValue.NONE).getColor()
                Text(
                    text = player.getScoreString(),
                    fontSize = 4.em,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = textColor
                )
            }
        }
        if (isFirstBidder) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_brightness_auto_24),
                contentDescription = "Bidder icon",
                modifier = Modifier
                    .size(height / 3)
                    .align(Alignment.BottomEnd)
            )
        } else if (isCardGiver) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_dry_24),
                contentDescription = "Giver icon",
                modifier = Modifier
                    .size(height / 3)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}

@Composable
fun HistoryGameScreen(
    players: List<PlayerWithScore>,
    rounds: List<SkatRound>,
    roundInformationState: SkatRoundInformationState,
    onRoundStateChange: (SkatRound, Player) -> Unit
) {
    var showHistoryInfo by remember { mutableStateOf(false) }

    if (showHistoryInfo) {
        SkatRoundInformation(
            skatRound = roundInformationState.round ,
            player = roundInformationState.player,
            onDismissRequest = { showHistoryInfo = false }
        )
    }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Spacer(
                modifier = Modifier
                    .width(65.dp)
            )
            val boxSize = 80.dp
            Box(
                modifier = Modifier
                    .width(boxSize)
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = players[0].getPlayerName(),
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 4.em,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    val scoreColor = if (players[0].getScore() > 0) TextPainter(PaintValue.GREEN).getColor()
                    else if(players[0].getScore() < 0) TextPainter(PaintValue.RED).getColor()
                    else TextPainter(PaintValue.NONE).getColor()
                    Text(
                        text = players[0].getScoreString(),
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 3.em,
                        color = scoreColor
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .width(1.dp)
            )
            Box(
                modifier = Modifier
                    .width(boxSize)
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = players[1].getPlayerName(),
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 4.em,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    val scoreColor = if (players[1].getScore() > 0) TextPainter(PaintValue.GREEN).getColor()
                    else if(players[1].getScore() < 0) TextPainter(PaintValue.RED).getColor()
                    else TextPainter(PaintValue.NONE).getColor()
                    Text(
                        text = players[1].getScoreString(),
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 3.em,
                        color = scoreColor
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .width(1.dp)
            )
            Box(
                modifier = Modifier
                    .width(boxSize)
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = players[2].getPlayerName(),
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 4.em,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    val scoreColor = if (players[2].getScore() > 0) TextPainter(PaintValue.GREEN).getColor()
                    else if(players[2].getScore() < 0) TextPainter(PaintValue.RED).getColor()
                    else TextPainter(PaintValue.NONE).getColor()
                    Text(
                        text = players[2].getScoreString(),
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 3.em,
                        color = scoreColor
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .width(65.dp)
            )
        }
        LazyColumn() {
            var currentScorePlayerOne = 0
            var currentScorePlayerTwo = 0
            var currentScorePlayerThree = 0
            itemsIndexed(rounds) { _, round ->
                currentScorePlayerOne += round.pointsGainedPlayerOne
                currentScorePlayerTwo += round.pointsGainedPlayerTwo
                currentScorePlayerThree += round.pointsGainedPlayerThree
                HistoryRound(
                    currentScorePlayerOne,
                    currentScorePlayerTwo,
                    currentScorePlayerThree,
                    round = round,
                    onClick = {
                        showHistoryInfo = true
                        val player = if(round.pointsGainedPlayerOne != 0) players[0].player else if (round.pointsGainedPlayerTwo != 0) players[1].player else players[2].player
                        onRoundStateChange(round, player)
                    }
                )
            }
        }
    }
}

@Composable
fun HistoryRound(
    currentScorePlayerOne: Int,
    currentScorePlayerTwo: Int,
    currentScorePlayerThree: Int,
    round: SkatRound,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        Text(
            text = round.roundIndex.toString(),
            modifier = Modifier
                .width(60.dp)
                .padding(end = 5.dp),
            textAlign = TextAlign.Center,
            fontSize = 5.em
        )

        Divider(color = Color.Gray, modifier = Modifier
            .height(47.dp)
            .width(1.dp))

        val boxSize = 80.dp
        Box(
            modifier = Modifier
                .width(boxSize)
                .fillMaxHeight()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                val roundColor = if (currentScorePlayerOne > 0) TextPainter(PaintValue.GREEN).getColor()
                else if(currentScorePlayerOne < 0) TextPainter(PaintValue.RED).getColor()
                else TextPainter(PaintValue.NONE).getColor()
                Text(
                    text = currentScorePlayerOne.toString(),
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 4.em,
                    color = roundColor
                )
                val roundScoreColor = if (round.pointsGainedPlayerOne > 0) TextPainter(PaintValue.GREEN).getColor()
                else if(round.pointsGainedPlayerOne < 0) TextPainter(PaintValue.RED).getColor()
                else TextPainter(PaintValue.NONE).getColor()
                Text(
                    text = round.pointsGainedPlayerOne.toString(),
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 3.em,
                    color = roundScoreColor
                )
            }
        }

        Divider(color = Color.Gray, modifier = Modifier
            .height(47.dp)
            .width(1.dp))

        Box(
            modifier = Modifier
                .width(boxSize)
                .fillMaxHeight()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                val roundColor = if (currentScorePlayerTwo > 0) TextPainter(PaintValue.GREEN).getColor()
                else if(currentScorePlayerTwo < 0) TextPainter(PaintValue.RED).getColor()
                else TextPainter(PaintValue.NONE).getColor()
                Text(
                    text = currentScorePlayerTwo.toString(),
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 4.em,
                    color = roundColor
                )
                val roundScoreColor = if (round.pointsGainedPlayerTwo > 0) TextPainter(PaintValue.GREEN).getColor()
                else if(round.pointsGainedPlayerTwo < 0) TextPainter(PaintValue.RED).getColor()
                else TextPainter(PaintValue.NONE).getColor()
                Text(
                    text = round.pointsGainedPlayerTwo.toString(),
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 3.em,
                    color = roundScoreColor
                )
            }
        }

        Divider(color = Color.Gray, modifier = Modifier
            .height(47.dp)
            .width(1.dp))

        Box(
            modifier = Modifier
                .width(boxSize)
                .fillMaxHeight()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                val roundColor = if (currentScorePlayerThree > 0) TextPainter(PaintValue.GREEN).getColor()
                else if(currentScorePlayerThree < 0) TextPainter(PaintValue.RED).getColor()
                else TextPainter(PaintValue.NONE).getColor()
                Text(
                    text = currentScorePlayerThree.toString(),
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 4.em,
                    color = roundColor
                )
                val roundScoreColor = if (round.pointsGainedPlayerThree > 0) TextPainter(PaintValue.GREEN).getColor()
                else if(round.pointsGainedPlayerThree < 0) TextPainter(PaintValue.RED).getColor()
                else TextPainter(PaintValue.NONE).getColor()
                Text(
                    text = round.pointsGainedPlayerThree.toString(),
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 3.em,
                    color = roundScoreColor
                )
            }
        }

        Divider(color = Color.Gray, modifier = Modifier
            .height(47.dp)
            .width(1.dp))

        Icon(
            painter = painterResource(id = R.drawable.baseline_info_24),
            contentDescription = "Round info",
            modifier = Modifier
                .width(60.dp)
        )
    }
}

@Composable
fun Header(
    selectedScreen: SkatScreen,
    onHeaderClicked: (screen: SkatScreen) -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        ),
        shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 5.dp, bottomEnd = 5.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp, end = 50.dp, top = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.33f)
                    .clickable {
                        onHeaderClicked(SkatScreen.BID_TABLE)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Table",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 6.em,
                    fontWeight = if (selectedScreen == SkatScreen.BID_TABLE) FontWeight.Bold else FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
                if (selectedScreen == SkatScreen.BID_TABLE) {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .height(3.dp),
                        color = Color.Magenta
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .clickable {
                        onHeaderClicked(SkatScreen.GAME_SCREEN)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Game",
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontSize = 6.em,
                    fontWeight = if (selectedScreen == SkatScreen.GAME_SCREEN) FontWeight.Bold else FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
                if (selectedScreen == SkatScreen.GAME_SCREEN) {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .height(3.dp),
                        color = Color.Magenta
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onHeaderClicked(SkatScreen.ROUND_HISTORY)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "History",
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontSize = 6.em,
                    fontWeight = if (selectedScreen == SkatScreen.ROUND_HISTORY) FontWeight.Bold else FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
                if (selectedScreen == SkatScreen.ROUND_HISTORY) {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .height(3.dp),
                        color = Color.Magenta
                    )
                }
            }
        }
    }
}

private fun constructSkatRound(roundState: SkatRoundState, isBockRound: Boolean, gameId: String, roundIndex: Int, roundScore: Int, players: List<PlayerWithScore>) : SkatRound {
    val declarations = mutableListOf<Declaration>()
    if (roundState.reChecked) declarations.add(Declaration.RE)
    if (roundState.kontraChecked) declarations.add(Declaration.KONTRA)
    if (roundState.ouverChecked) declarations.add(Declaration.OUVER)
    if (roundState.handChecked) declarations.add(Declaration.HAND)
    if (roundState.schneiderChecked) declarations.add(Declaration.SCHNEIDER)
    if (roundState.schwarzChecked) declarations.add(Declaration.SCHWARZ)
    val outcomes = mutableListOf<RoundOutcome>()
    if (roundState.successfulSchwarz) outcomes.add(RoundOutcome.SUCCESSFUL_SCHWARZ)
    if (roundState.successfulSchneider) outcomes.add(RoundOutcome.SUCCESSFUL_SCHNEIDER)
    if (roundState.jungfrauChecked) outcomes.add(RoundOutcome.JUNGFRAU)
    if (roundState.successfulDurchmarschChecked) outcomes.add(RoundOutcome.DURCHMARSCH)
    val roundModifier = RoundModifier(
        declarations = declarations,
        roundOutcomes = outcomes
    )
    return SkatRound(
        pointsGainedPlayerOne = if (roundState.selectedPlayer.player.playerId == players[0].player.playerId) roundScore else 0,
        pointsGainedPlayerTwo = if (roundState.selectedPlayer.player.playerId == players[1].player.playerId) roundScore else 0,
        pointsGainedPlayerThree = if (roundState.selectedPlayer.player.playerId == players[2].player.playerId) roundScore else 0,
        roundModifier = roundModifier,
        isBockRound = isBockRound,
        trickColor = roundState.selectedTrick,
        roundType = roundState.selectedRoundType,
        roundVariant = roundState.roundVariant,
        roundIndex = roundIndex,
        skatGameId = gameId
    )
}

private fun decideCardGiverAndFirstBidder(roundCount: Int) : Pair<Int, Int> {
    if (roundCount == 1) {
        return Pair(0, 2)
    }
    if (roundCount == 2) {
        return Pair(1, 0)
    }
    if (roundCount == 3) {
        return Pair(2, 1)
    }
    var tmp = roundCount
    while ((tmp - 3) >= 0) {
        tmp -= 3
    }
    return Pair(tmp, if (tmp == 0) 2 else if(tmp == 1) 0 else 1)
}

private fun addSpecialRounds(currentSpecialRound: List<SpecialRound>, toAddSpecialRounds: List<SpecialRound>) : List<SpecialRound> {
    val tmp = currentSpecialRound.toMutableList()
    if (tmp.size > 0) tmp.removeAt(0)
    for(round in toAddSpecialRounds) {
        tmp.add(round)
    }
    return tmp
}

@Preview
@Composable
fun PreviewMainGameScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainGameScreen(
            players = listOf(
                PlayerWithScore(
                    Player("sdfs"),
                    Score(
                        score = 231
                    )
                ),
                PlayerWithScore(
                    Player("dsfsfs"),
                    Score(
                        score = 0
                    )
                ),
                PlayerWithScore(
                    Player("yyre"),
                    Score(
                        score = -23
                    )
                ),
            ),
            showBottomSheet = false,
            roundState = SkatRoundState(
                roundVariant = RoundVariant.NORMAL
            ),
            currentRound = 5,
            gameId = "",
            specialRounds = listOf(SpecialRound.BOCK),
            onSkatRoundEvent = {},
            onSkatGameEvent = {},
            onShowBottomSheetChanged = {}
        )
    }
}

@Preview
@Composable
fun PreviewPlayerBox() {
    val player = SampleRepository().getPlayerWithScore()
    PlayerBox(
        player = player,
        isFirstBidder = true,
        isCardGiver = false,
        height = 75.dp
    )
}

@Preview
@Composable
fun PreviewHeader() {
    Header(
        SkatScreen.GAME_SCREEN,
        onHeaderClicked = {}
    )
}

@Preview
@Composable
fun PreviewHistoryRound() {
    val round = SampleRepository().getSkatRound()
    HistoryRound(
        currentScorePlayerOne = 0,
        currentScorePlayerTwo = 0,
        currentScorePlayerThree = 0,
        round = round,
        onClick = {}
    )
}

@Preview
@Composable
fun PreviewHistoryGameScreen() {
    val players = SampleRepository().getPlayersWithScore()
    val round = SampleRepository().getSkatRound()
    HistoryGameScreen(
        players = players,
        rounds = listOf(round),
        roundInformationState = SkatRoundInformationState(),
        onRoundStateChange = {_,_->}
    )
}