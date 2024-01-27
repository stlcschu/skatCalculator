package com.example.skatcalculator.composables

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.skatcalculator.R
import com.example.skatcalculator.database.events.SkatGameEvent
import com.example.skatcalculator.database.events.SkatRoundEvent
import com.example.skatcalculator.database.tables.Player
import com.example.skatcalculator.database.tables.Score
import com.example.skatcalculator.database.tables.SkatGameWithRounds
import com.example.skatcalculator.database.tables.SkatGameWithRoundsAndScores
import com.example.skatcalculator.database.tables.SkatRound
import com.example.skatcalculator.database.tables.SpecialRounds
import com.example.skatcalculator.dataclasses.PlayerWithScore
import com.example.skatcalculator.dataclasses.RoundModifier
import com.example.skatcalculator.enums.Declaration
import com.example.skatcalculator.enums.RoundOutcome
import com.example.skatcalculator.enums.RoundVariant
import com.example.skatcalculator.enums.RoundType
import com.example.skatcalculator.enums.SpecialRound
import com.example.skatcalculator.enums.TrickColor
import com.example.skatcalculator.states.SkatRoundInformationState
import com.example.skatcalculator.states.SkatRoundState
import com.example.skatcalculator.util.GroupPlayerWithScore
import com.example.skatcalculator.util.ScoreCalculator
import kotlin.math.round
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
        modifier = Modifier.fillMaxSize()
    ) {
        if (players.isEmpty()) {
            LinearProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
            return
        }
        var currentContent by remember { mutableIntStateOf(1) }
        Header(
            onHeaderClicked = { value ->
                currentContent = value
            }
        )
        if (currentContent == 1) {
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
            return
        }
        if (currentContent == 2) {
            SkatTable()
            return
        }
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
    Column(

    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(

            ) {
                Text(text = players[0].getPlayerName())
                Text(text = players[0].getScoreString())
            }
            Column(

            ) {
                Text(text = players[1].getPlayerName())
                Text(text = players[1].getScoreString())
            }
            Column(

            ) {
                Text(text = players[2].getPlayerName())
                Text(text = players[2].getScoreString())
            }
        }
    }
    Text(text = "Current Round")
    val giverAndBidder = decideCardGiverAndFirstBidder(currentRound)
    val roundGiver = players[giverAndBidder.first]
    Text(text = "${roundGiver.getPlayerName()} gives cards")

    val bidderOne = players[giverAndBidder.second.first]
    val bidderTwo = players[giverAndBidder.second.second]
    Text(text = "${bidderOne.getPlayerName()} bids ${bidderTwo.getPlayerName()}")

    Text(text = if (currentSpecialRound == SpecialRound.RAMSCH) "RAMSCH" else if (currentSpecialRound == SpecialRound.BOCK) "BOCK" else "")

    if (currentSpecialRound == SpecialRound.RAMSCH) {
        ExposedDropdownMenuBox(
            expanded = roundState.shoveDropdownExpanded,
            onExpandedChange = {
                onSkatRoundEvent(
                    SkatRoundEvent.onShoveDropdownExpandedChanged(
                        !roundState.shoveDropdownExpanded
                    )
                )
            }
        ) {
            TextField(
                readOnly = true,
                value = roundState.selectedShove.toString(),
                onValueChange = {},
                label = { Text("Shoves") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = roundState.shoveDropdownExpanded
                    ) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor()
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
    } else {
        Row {
            Column {
                Row {
                    RadioButton(
                        selected = roundState.roundVariant == RoundVariant.NORMAL,
                        onClick = { onSkatRoundEvent(
                            SkatRoundEvent.onRoundVariantChanged(RoundVariant.NORMAL)
                        ) }
                    )
                    Text(text = "Normal")
                }
                Row {
                    RadioButton(
                        selected = roundState.roundVariant == RoundVariant.GRAND,
                        onClick = { onSkatRoundEvent(
                            SkatRoundEvent.onRoundVariantChanged(RoundVariant.GRAND)
                        ) }
                    )
                    Text(text = "Grand")
                }
            }
            Column {
                Row {
                    RadioButton(
                        selected = roundState.roundVariant == RoundVariant.NULLSPIEL,
                        onClick = { onSkatRoundEvent(
                            SkatRoundEvent.onRoundVariantChanged(RoundVariant.NULLSPIEL)
                        ) }
                    )
                    Text(text = "Nullspiel")
                }
                Row {
                    RadioButton(
                        selected = roundState.roundVariant == RoundVariant.RAMSCH,
                        onClick = { onSkatRoundEvent(
                            SkatRoundEvent.onRoundVariantChanged(RoundVariant.RAMSCH)
                        ) }
                    )
                    Text(text = "Ramsch")
                }
            }
        }
        when(roundState.roundVariant) {
            RoundVariant.NORMAL -> {
                ExposedDropdownMenuBox(
                    expanded = roundState.playerDropdownExpanded,
                    onExpandedChange = {
                        onSkatRoundEvent(
                            SkatRoundEvent.onPlayerDropdownExpandedChanged(!roundState.playerDropdownExpanded)
                        )
                    }
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
                        modifier = Modifier.menuAnchor()
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
                Row() {
                    Icon(
                        painter = painterResource(id = if(roundState.selectedTrick == TrickColor.CROSSES) R.drawable.symbol_cross else R.drawable.symbol_cross_hollow),
                        contentDescription = "Crosses selection button",
                        modifier = Modifier.clickable {
                            onSkatRoundEvent(
                                SkatRoundEvent.onSelectedTrickChanged(
                                    TrickColor.CROSSES
                                )
                            )
                        },
                        tint = Color.Unspecified
                    )
                    Icon(
                        painter = painterResource(id = if(roundState.selectedTrick == TrickColor.SPADES) R.drawable.symbol_spade else R.drawable.symbol_spade_hollow),
                        contentDescription = "Crosses selection button",
                        modifier = Modifier.clickable {
                            onSkatRoundEvent(
                                SkatRoundEvent.onSelectedTrickChanged(
                                    TrickColor.SPADES
                                )
                            )
                        },
                        tint = Color.Unspecified
                    )
                    Icon(
                        painter = painterResource(id = if(roundState.selectedTrick == TrickColor.HEARTS) R.drawable.symbol_heart else R.drawable.symbol_heart_hollow),
                        contentDescription = "Crosses selection button",
                        modifier = Modifier.clickable {
                            onSkatRoundEvent(
                                SkatRoundEvent.onSelectedTrickChanged(
                                    TrickColor.HEARTS
                                )
                            )
                        },
                        tint = Color.Unspecified
                    )
                    Icon(
                        painter = painterResource(id = if(roundState.selectedTrick == TrickColor.DIAMONDS) R.drawable.symbol_diamond else R.drawable.symbol_diamond_hollow),
                        contentDescription = "Crosses selection button",
                        modifier = Modifier.clickable {
                            onSkatRoundEvent(
                                SkatRoundEvent.onSelectedTrickChanged(
                                    TrickColor.DIAMONDS
                                )
                            )
                        },
                        tint = Color.Unspecified
                    )
                }
                Row() {
                    Checkbox(
                        checked = roundState.reChecked,
                        onCheckedChange = { isChecked ->
                            onSkatRoundEvent(
                                SkatRoundEvent.onReCheckedChanged(isChecked)
                            )
                        }
                    )
                    Text(text = "Re")
                    Checkbox(
                        checked = roundState.kontraChecked,
                        onCheckedChange = { isChecked ->
                            onSkatRoundEvent(
                                SkatRoundEvent.onKontraCheckedChanged(isChecked)
                            )
                        }
                    )
                    Text(text = "Kontra")
                }
                Row() {
                    Checkbox(
                        checked = roundState.ouverChecked,
                        onCheckedChange = { isChecked ->
                            onSkatRoundEvent(
                                SkatRoundEvent.onOuverCheckedChanged(isChecked)
                            )
                        }
                    )
                    Text(text = "Ouver")
                    Checkbox(
                        checked = roundState.handChecked,
                        onCheckedChange = { isChecked ->
                            onSkatRoundEvent(
                                SkatRoundEvent.onHandCheckedChanged(isChecked)
                            )
                        }
                    )
                    Text(text = "Hand")
                }
                Row() {
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
                    Text(text = "Schneider")
                    Checkbox(
                        checked = roundState.schwarzChecked,
                        onCheckedChange = { isChecked ->
                            onSkatRoundEvent(
                                SkatRoundEvent.onSchwarzCheckedChanged(isChecked)
                            )
                        },
                        enabled = roundState.schneiderChecked
                    )
                    Text(text = "Schwarz")
                }
            }
            RoundVariant.GRAND -> {
                ExposedDropdownMenuBox(
                    expanded = roundState.playerDropdownExpanded,
                    onExpandedChange = {
                        onSkatRoundEvent(
                            SkatRoundEvent.onPlayerDropdownExpandedChanged(
                                !roundState.playerDropdownExpanded
                            )
                        )
                    }
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
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = roundState.playerDropdownExpanded,
                        onDismissRequest = {
                            onSkatRoundEvent(
                                SkatRoundEvent.onPlayerDropdownExpandedChanged(
                                    false
                                )
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
                                        SkatRoundEvent.onPlayerDropdownExpandedChanged(
                                            false
                                        )
                                    )
                                },
                                text = { Text(text = selectionOption.getPlayerName()) }
                            )
                        }
                    }
                }
                Row() {
                    Checkbox(
                        checked = roundState.reChecked,
                        onCheckedChange = { isChecked ->
                            onSkatRoundEvent(
                                SkatRoundEvent.onReCheckedChanged(isChecked)
                            )
                        }
                    )
                    Text(text = "Re")
                    Checkbox(
                        checked = roundState.kontraChecked,
                        onCheckedChange = { isChecked ->
                            onSkatRoundEvent(
                                SkatRoundEvent.onKontraCheckedChanged(isChecked)
                            )
                        }
                    )
                    Text(text = "Kontra")
                }
                Row() {
                    Checkbox(
                        checked = roundState.ouverChecked,
                        onCheckedChange = { isChecked ->
                            onSkatRoundEvent(
                                SkatRoundEvent.onOuverCheckedChanged(isChecked)
                            )
                        }
                    )
                    Text(text = "Ouver")
                    Checkbox(
                        checked = roundState.handChecked,
                        onCheckedChange = { isChecked ->
                            onSkatRoundEvent(
                                SkatRoundEvent.onHandCheckedChanged(isChecked)
                            )
                        }
                    )
                    Text(text = "Hand")
                }
                Row() {
                    Checkbox(
                        checked = roundState.schneiderChecked,
                        onCheckedChange = { isChecked ->
                            onSkatRoundEvent(
                                SkatRoundEvent.onSchneiderCheckedChanged(isChecked)
                            )
                        }
                    )
                    Text(text = "Schneider")
                    Checkbox(
                        checked = roundState.schwarzChecked,
                        onCheckedChange = { isChecked ->
                            onSkatRoundEvent(
                                SkatRoundEvent.onSchwarzCheckedChanged(isChecked)
                            )
                        }
                    )
                    Text(text = "Schwarz")
                }
            }
            RoundVariant.NULLSPIEL -> {
                ExposedDropdownMenuBox(
                    expanded = roundState.playerDropdownExpanded,
                    onExpandedChange = {
                        onSkatRoundEvent(
                            SkatRoundEvent.onPlayerDropdownExpandedChanged(
                                !roundState.playerDropdownExpanded
                            )
                        )
                    }
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
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = roundState.playerDropdownExpanded,
                        onDismissRequest = {
                            onSkatRoundEvent(
                                SkatRoundEvent.onPlayerDropdownExpandedChanged(
                                    false
                                )
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
                                        SkatRoundEvent.onPlayerDropdownExpandedChanged(
                                            false
                                        )
                                    )
                                },
                                text = { Text(text = selectionOption.getPlayerName()) }
                            )
                        }
                    }
                }
                Row() {
                    Checkbox(
                        checked = roundState.ouverChecked,
                        onCheckedChange = { isChecked ->
                            onSkatRoundEvent(
                                SkatRoundEvent.onOuverCheckedChanged(isChecked)
                            )
                        }
                    )
                    Text(text = "Ouver")
                    Checkbox(
                        checked = roundState.handChecked,
                        onCheckedChange = { isChecked ->
                            onSkatRoundEvent(
                                SkatRoundEvent.onHandCheckedChanged(isChecked)
                            )
                        }
                    )
                    Text(text = "Hand")
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
                    }
                ) {
                    TextField(
                        readOnly = true,
                        value = roundState.selectedShove.toString(),
                        onValueChange = {},
                        label = { Text("Shoves") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = roundState.shoveDropdownExpanded
                            ) },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier.menuAnchor()
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
        }
    }


    Row() {
        val calculatedGainLoss = ScoreCalculator()
            .calculateDisplayScore(
                skatRoundState = roundState,
                isBockRound = currentSpecialRound == SpecialRound.BOCK
            )
        Column() {
            Text(text = "Potential gain")
            Text(text = "${calculatedGainLoss.first}")
            Icon(painter = painterResource(id = R.drawable.baseline_trending_up_24), contentDescription = "Score trend icon")
        }
        Column() {
            Text(text = "Potential loss")
            Text(text = "${calculatedGainLoss.second}")
            Icon(painter = painterResource(id = R.drawable.baseline_trending_down_24), contentDescription = "Score trend icon")
        }
    }
    val context = LocalContext.current
    Button(onClick = {
        if (roundState.selectedPlayer.player.isEmpty() && roundState.roundVariant != RoundVariant.RAMSCH) {
            Toast.makeText(context, "No player selected", Toast.LENGTH_SHORT).show()
            return@Button
        }
        onShowBottomSheetChanged(true)
    }) {
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
            when(roundState.roundVariant) {
                RoundVariant.RAMSCH -> {
                    ExposedDropdownMenuBox(
                        expanded = roundState.playerDropdownExpanded,
                        onExpandedChange = {
                            onSkatRoundEvent(
                                SkatRoundEvent.onPlayerDropdownExpandedChanged(!roundState.playerDropdownExpanded)
                            )
                        }
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
                            modifier = Modifier.menuAnchor()
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
                    Row() {
                        Column(
                            modifier = Modifier.clickable {
                                onSkatRoundEvent(
                                    SkatRoundEvent.onRoundScoreValueChanged(if (roundState.roundScore - 1f < 0) roundState.roundScore else roundState.roundScore - 1f)
                                )
                            }
                        ) {
                            Text(text = roundState.selectedPlayer.getPlayerName())
                            Text(text = roundState.roundScore.roundToInt().toString())
                        }
                        Slider(
                            value = roundState.roundScore,
                            onValueChange = {
                                onSkatRoundEvent(
                                    SkatRoundEvent.onRoundScoreValueChanged(it)
                                )
                            },
                            valueRange = 0f ..120f,
                            steps = 119,
                            modifier = Modifier.fillMaxWidth(0.7f)
                        )
                        Column(
                            modifier = Modifier.clickable {
                                onSkatRoundEvent(
                                    SkatRoundEvent.onRoundScoreValueChanged(if (roundState.roundScore + 1f > 120) 120f else roundState.roundScore + 1f)
                                )
                            }
                        ) {
                            Text(text = "Others")
                            Text(text = (120 - roundState.roundScore.roundToInt()).toString())
                        }
                    }
                    Row() {
                        if (roundState.roundScore.roundToInt() == 120) {
                            Checkbox(
                                checked = roundState.successfulDurchmarschChecked,
                                onCheckedChange = { isChecked ->
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onSuccessfulDurchmarschCheckedChanged(isChecked)
                                    )
                                    onSkatRoundEvent(
                                        SkatRoundEvent.onJungfrauCheckedChanged(isChecked)
                                    )
                                }
                            )
                            Text(text = "Successful Durchmarsch")
                        }
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
                    if (!roundState.selectedPlayer.player.isEmpty()) {
                        Row() {
                            val playerScore = roundState.selectedPlayer.score.score
                            val calculatedGainLoss = ScoreCalculator()
                                .calculatePotentialSingleScore(
                                    skatRoundState = roundState,
                                    isBockRound = currentSpecialRound == SpecialRound.BOCK
                                )
                            Column() {
                                Text(text = "Current Score")
                                Text(text = "$playerScore")
                            }
                            Column() {
                                Text(text = "")
                                val iconId = if (calculatedGainLoss > 0) R.drawable.baseline_trending_up_24 else R.drawable.baseline_trending_down_24
                                Icon(painter = painterResource(id = iconId), contentDescription = "Score trend icon")
                                Text(text = "$calculatedGainLoss")
                            }
                            Column() {
                                Text(text = "After Round")
                                Text(text = "${playerScore + calculatedGainLoss}")
                            }
                        }
                    }
                }
                RoundVariant.NULLSPIEL -> {
                    Row() {
                        Switch(
                            checked = roundState.successfulNullSpielChecked,
                            onCheckedChange = {
                                onSkatRoundEvent(
                                    SkatRoundEvent.onNullSpielCheckedChanged(it)
                                )
                            }
                        )
                        Text(text = if (roundState.successfulNullSpielChecked) "Nullspiel successful" else "Nullspiel failed")
                    }
                    if (!roundState.selectedPlayer.player.isEmpty()) {
                        Row() {
                            val playerScore = roundState.selectedPlayer.score.score
                            val calculatedGainLoss = ScoreCalculator()
                                .calculatePotentialSingleScore(
                                    skatRoundState = roundState,
                                    isBockRound = currentSpecialRound == SpecialRound.BOCK
                                )
                            Column() {
                                Text(text = "Current Score")
                                Text(text = "$playerScore")
                            }
                            Column() {
                                Text(text = "")
                                val iconId = if (calculatedGainLoss > 0) R.drawable.baseline_trending_up_24 else R.drawable.baseline_trending_down_24
                                Icon(painter = painterResource(id = iconId), contentDescription = "Score trend icon")
                                Text(text = "$calculatedGainLoss")
                            }
                            Column() {
                                Text(text = "After Round")
                                Text(text = "${playerScore + calculatedGainLoss}")
                            }
                        }
                    }
                }
                else -> {
                    Row() {
                        Column(
                            modifier = Modifier.clickable {
                                onSkatRoundEvent(
                                    SkatRoundEvent.onRoundScoreValueChanged(if (roundState.roundScore - 1f < 0) roundState.roundScore else roundState.roundScore - 1f)
                                )
                            }
                        ) {
                            Text(text = roundState.selectedPlayer.getPlayerName())
                            Text(text = roundState.roundScore.roundToInt().toString())
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
                                    SkatRoundEvent.onSuccessfulSchneiderChanged (
                                        if  (roundState.schneiderChecked) roundState.roundScore.roundToInt() > 90
                                        else false
                                    )
                                )
                            },
                            valueRange = 0f ..120f,
                            steps = 119,
                            modifier = Modifier.fillMaxWidth(0.7f)
                        )
                        Column(
                            modifier = Modifier.clickable {
                                onSkatRoundEvent(
                                    SkatRoundEvent.onRoundScoreValueChanged(if (roundState.roundScore + 1f > 120) 120f else roundState.roundScore + 1f)
                                )
                            }
                        ) {
                            Text(text = "Others")
                            Text(text = (120 - roundState.roundScore.roundToInt()).toString())
                        }
                    }
                    if (!roundState.selectedPlayer.player.isEmpty()) {
                        Row() {
                            val playerScore = roundState.selectedPlayer.score.score
                            val calculatedGainLoss = ScoreCalculator()
                                .calculatePotentialSingleScore(
                                    skatRoundState = roundState,
                                    isBockRound = currentSpecialRound == SpecialRound.BOCK
                                )
                            Column() {
                                Text(text = "Current Score")
                                Text(text = "$playerScore")
                            }
                            Column() {
                                Text(text = "")
                                val iconId = if (calculatedGainLoss > 0) R.drawable.baseline_trending_up_24 else R.drawable.baseline_trending_down_24
                                Icon(painter = painterResource(id = iconId), contentDescription = "Score trend icon")
                                Text(text = "$calculatedGainLoss")
                            }
                            Column() {
                                Text(text = "After Round")
                                Text(text = "${playerScore + calculatedGainLoss}")
                            }
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
                                ) },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                modifier = Modifier.menuAnchor()
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
                    if(roundState.schwarzChecked && roundState.roundScore.roundToInt() == 120) {
                        Checkbox(
                            checked = roundState.successfulSchwarz ,
                            onCheckedChange = {isChecked ->
                                onSkatRoundEvent(
                                    SkatRoundEvent.onSuccessfulSchwarzCheckedChanged(isChecked)
                                )
                            }
                        )
                    }
                }
            }
            Button(onClick = {
                if (roundState.selectedPlayer.player.isEmpty() && roundState.roundVariant != RoundVariant.RAMSCH) {
                    Toast.makeText(context, "No player selected", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                onShowBottomSheetChanged(false)
                val scoreAndSpecialRounds = ScoreCalculator().calculateFinalScoreWithSpecialRounds(roundState, currentSpecialRound == SpecialRound.BOCK)
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
                val newScore = roundState.selectedPlayer.score.score + scoreAndSpecialRounds.first
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
                val newSpecialRounds = addSpecialRounds(specialRounds, scoreAndSpecialRounds.second)
                onSkatGameEvent(
                    SkatGameEvent.saveSpecialRounds(
                        SpecialRounds(
                            specialRounds = newSpecialRounds,
                            gameId = gameId
                        )
                    )
                )
            }) {
                Text(text = "End Round")
            }
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

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(

        ) {

            Text(
                text = players[0].getPlayerName(),
                modifier = Modifier
                    .fillMaxWidth(0.33f)
            )
            Text(text = players[0].getScoreString())
        }
        Column(

        ) {
            Text(
                text = players[1].getPlayerName(),
                modifier = Modifier
                    .fillMaxWidth(0.5f)
            )
            Text(text = players[1].getScoreString())
        }
        Column(

        ) {
            Text(
                text = players[2].getPlayerName(),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(text = players[2].getScoreString())
        }
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
            .clickable { onClick() }
    ) {
        Text(text = round.roundIndex.toString())
        Column(

        ) {
            Text(
                text = currentScorePlayerOne.toString()
            )
            Text(text = round.pointsGainedPlayerOne.toString())
        }
        Column(

        ) {
            Text(
                text = currentScorePlayerTwo.toString()
            )
            Text(text = round.pointsGainedPlayerTwo.toString())
        }
        Column(

        ) {
            Text(
                text = currentScorePlayerThree.toString()
            )
            Text(text = round.pointsGainedPlayerThree.toString())
        }
    }
}

@Composable
fun Header(
    onHeaderClicked: (index: Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Game",
            modifier = Modifier
                .fillMaxWidth(0.33f)
                .clickable {
                    onHeaderClicked(1)
                }
        )
        Text(
            text = "Table",
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .clickable {
                    onHeaderClicked(2)
                }
        )
        Text(
            text = "History",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onHeaderClicked(3)
                }
        )
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

private fun decideCardGiverAndFirstBidder(roundCount: Int) : Pair<Int, Pair<Int, Int>> {
    if (roundCount == 1) {
        return Pair(0, Pair(2, 1))
    }
    if (roundCount == 2) {
        return Pair(1, Pair(0, 2))
    }
    if (roundCount == 3) {
        return Pair(2, Pair(1, 0))
    }
    var tmp = roundCount
    while ((tmp - 3) >= 0) {
        tmp -= 3
    }
    return Pair(tmp, if (tmp == 0) Pair(2, 1) else if(tmp == 1) Pair(0, 2) else Pair(1, 0))
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
fun SkatGameScreenPreview() {
//    SkatGameScreen(skatGame = SampleRepository().getSampleSkatGame(), onSkatGameEvent = {})
}