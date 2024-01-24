package com.example.skatcalculator.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.skatcalculator.database.events.PlayerEvent
import com.example.skatcalculator.database.events.SkatGameEvent
import com.example.skatcalculator.database.tables.Player
import com.example.skatcalculator.database.tables.Score
import com.example.skatcalculator.database.tables.SkatGame
import com.example.skatcalculator.database.tables.SkatGameWithScores
import com.example.skatcalculator.database.tables.SpecialRounds
import com.example.skatcalculator.helper.SampleRepository
import com.example.skatcalculator.util.GroupPlayerWithScore
import com.example.skatcalculator.util.IdGenerator
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainMenuScreen(
    players: List<Player>,
    historyGames: List<SkatGameWithScores>,
    onPlayerEvent: (PlayerEvent) -> Unit,
    onSkatGameEvent: (SkatGameEvent) -> Unit,
    onClickStartSkatGame: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var showLoadingHistory by remember { mutableStateOf(true) }

        LaunchedEffect(key1 = Unit){
            delay(5000)
            showLoadingHistory = false
        }

        var playerOne by remember { mutableStateOf(Player("")) }
        var playerTwo by remember { mutableStateOf(Player("")) }
        var playerThree by remember { mutableStateOf(Player("")) }
        var showNewPlayerDialog by remember { mutableStateOf(false) }
        var addPlayerOnIndex by remember { mutableIntStateOf(0) }
        val gameIsReady = !playerOne.isEmpty() && !playerTwo.isEmpty() && !playerThree.isEmpty()


        if (showNewPlayerDialog) {
            Dialog(onDismissRequest = {
                showNewPlayerDialog = false
            }) {
                var newPlayerInput by remember { mutableStateOf("") }
                Card {
                    LazyColumn() {
                        items(players) { player ->
                            Card(
                                onClick = {
                                    when(addPlayerOnIndex) {
                                        2 -> {
                                            playerTwo = player
                                        }
                                        3 -> {
                                            playerThree = player
                                        }
                                        else -> {
                                            playerOne = player
                                        }
                                    }
                                    showNewPlayerDialog = false
                                }
                            ) {
                                Row(

                                ) {
                                    Text(text = player.name)
                                    IconButton(onClick = {
                                        onPlayerEvent(
                                            PlayerEvent.deletePlayer(player)
                                        )
                                        if (playerOne.equalsPlayer(player)) {
                                            playerOne = Player("")
                                        }
                                        if (playerTwo.equalsPlayer(player)){
                                            playerTwo = Player("")
                                        }
                                        if (playerThree.equalsPlayer(player)) {
                                            playerThree = Player("")
                                        }
                                    }) {
                                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove player")
                                    }
                                }
                            }
                        }
                        item {
                            Card {
                                Row {
                                    TextField(
                                        value = newPlayerInput,
                                        onValueChange = {
                                            newPlayerInput = it
                                        }
                                    )
                                    IconButton(onClick = {
                                        if (newPlayerInput.isNotEmpty() && newPlayerInput.isNotBlank()) {
                                            val player = Player(name = newPlayerInput, playerId = IdGenerator().generatePlayerId())
                                            when(addPlayerOnIndex) {
                                                2 -> {
                                                    playerTwo = player
                                                }
                                                3 -> {
                                                    playerThree = player
                                                }
                                                else -> {
                                                    playerOne = player
                                                }
                                            }
                                            showNewPlayerDialog = false
                                            onPlayerEvent(
                                                PlayerEvent.savePlayer(player)
                                            )
                                        }
                                    }) {
                                        Icon(imageVector = Icons.Default.Check, contentDescription = "Add new player")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        PlayerInsert(
            playerOne,
            onClick = {
                addPlayerOnIndex = 1
                showNewPlayerDialog = true
            },
            onRemove = {
                playerOne = Player("")
            }
        )
        PlayerInsert(
            playerTwo,
            onClick = {
                addPlayerOnIndex = 2
                showNewPlayerDialog = true
            },
            onRemove = {
                playerTwo = Player("")
            }
        )
        PlayerInsert(
            playerThree,
            onClick = {
                addPlayerOnIndex = 3
                showNewPlayerDialog = true
            },
            onRemove = {
                playerThree = Player("")
            }
        )
        Button(
            onClick = {
                val gameId = IdGenerator().generateGameId()
                val skatGame = SkatGame(
                    playerOne = playerOne,
                    playerTwo = playerTwo,
                    playerThree = playerThree,
                    skatGameId = gameId
                )
                onSkatGameEvent(
                    SkatGameEvent.saveSkatGame(skatGame)
                )
                onSkatGameEvent(
                    SkatGameEvent.saveScore(
                        Score(
                            score = 0,
                            skatGameId = gameId,
                            playerId = playerOne.playerId
                        )
                    )
                )
                onSkatGameEvent(
                        SkatGameEvent.saveScore(
                            Score(
                                score = 0,
                                skatGameId = gameId,
                                playerId = playerTwo.playerId
                            )
                        )
                )
                onSkatGameEvent(
                    SkatGameEvent.saveScore(
                        Score(
                            score = 0,
                            skatGameId = gameId,
                            playerId = playerThree.playerId
                        )
                    )
                )
                onSkatGameEvent(
                    SkatGameEvent.setSkatGameId(gameId)
                )
                onSkatGameEvent(
                    SkatGameEvent.saveSpecialRounds(SpecialRounds(specialRounds = emptyList(), gameId))
                )
                onClickStartSkatGame()
            },
            enabled = gameIsReady
        ) {
            Text(text = "Start game")
        }
        if (historyGames.isEmpty() && showLoadingHistory) {
            LinearProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
            return
        }
        if(historyGames.isEmpty()) {
            Text(text = "No Games found")
            return
        }
        LazyColumn() {
            items(historyGames) {game ->
                HistoryGamePreview(
                    game,
                    onClick = {
                        onSkatGameEvent(
                            SkatGameEvent.setSkatGameId(it)
                        )
                        onClickStartSkatGame()
                    }
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PlayerInsert(
    player: Player,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(

        ) {
            if (player.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Column {
                        Text(text = "Click to add player")
                        Divider()
                    }
                }
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Add new player")
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Column {
                        Text(text = player.name)
                        Divider()
                    }
                }
                IconButton(onClick = onRemove ) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Remove player")
                }
            }
        }
    }
}

@Composable
fun HistoryGamePreview(
    game: SkatGameWithScores,
    onClick: (String) -> Unit
) {
    val playerWithScores = GroupPlayerWithScore(game.skatGame.getPlayers(), game.scores).group()
    Row() {
        IconButton(onClick = {
            onClick(game.skatGame.skatGameId)
        }) {
            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Start history game")
        }
        Column() {
            Text(text = playerWithScores[0].getPlayerName())
            Text(text = playerWithScores[0].getScoreString())
        }
        Column() {
            Text(text = playerWithScores[1].getPlayerName())
            Text(text = playerWithScores[1].getScoreString())
        }
        Column() {
            Text(text = playerWithScores[2].getPlayerName())
            Text(text = playerWithScores[2].getScoreString())
        }
        Column() {
            Text(text = "${game.skatGame.lastPlayed.dayOfMonth}.${game.skatGame.lastPlayed.month.name} ${game.skatGame.lastPlayed.year}")
        }
    }
}

@Preview
@Composable
fun PreviewMainComposable() {
    MainMenuScreen(
        SampleRepository().getPlayerData(),
        emptyList(),
        onPlayerEvent = {},
        onSkatGameEvent = {}
    )
}