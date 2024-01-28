package com.example.skatcalculator.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
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
import java.time.Month

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
//        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = Color.LightGray)
            .fillMaxHeight()
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
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    Column {
                        LazyColumn(
                            modifier = Modifier
                                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                                .height(230.dp)
                        ) {
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
                                    },
                                    modifier = Modifier
                                        .padding()
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,

                                        ) {
                                        Text(
                                            text = player.name,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
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
                                Divider()
                            }
                        }
                        Row(
                            modifier = Modifier.height(70.dp)
                        ) {
                            TextField(
                                value = newPlayerInput,
                                onValueChange = {
                                    newPlayerInput = it
                                },
                                placeholder = { Text(text = "New player name") },
                                maxLines = 1,
                                shape = AbsoluteCutCornerShape(0.dp),
                                modifier = Modifier.fillMaxWidth(0.8f).fillMaxHeight()
                            )
                            IconButton(
                                onClick = {
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
                                },
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Add new player",
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 5.dp, bottomEnd = 5.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Spacer(
                modifier = Modifier.height(50.dp)
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                Spacer(
                    modifier = Modifier
                        .height(25.dp)
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
                Spacer(
                    modifier = Modifier
                        .height(25.dp)
                )
            }
        }
        if (historyGames.isEmpty() && showLoadingHistory) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
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
        modifier = Modifier
            .width(200.dp)
            .height(30.dp)
            .padding(top = 5.dp),
        shape = AbsoluteCutCornerShape(0.dp),
        backgroundColor = Color.White,
    ) {
        Row(
            modifier = Modifier
                .height(30.dp)
                .border(1.dp, Color.LightGray),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            if (player.isEmpty()) {
                Column {
                    Text(text = "Click to add player")
                }
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add new player",
                    modifier = Modifier.size(30.dp)
                )
            } else {
                Column {
                    Text(
                        text = player.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Remove player",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            onRemove()
                        }
                )
            }
        }
    }
    Divider(
        modifier = Modifier
            .width(200.dp)
            .height(2.dp)
    )
}

@Composable
fun HistoryGamePreview(
    game: SkatGameWithScores,
    onClick: (String) -> Unit
) {
    val playerWithScores = GroupPlayerWithScore(game.skatGame.getPlayers(), game.scores).group()
    Row(
        modifier = Modifier
            .height(50.dp)
            .padding(top = 10.dp)
            .background(color = Color.White),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                onClick(game.skatGame.skatGameId)
            },
            modifier = Modifier
                .fillMaxHeight()
                .width(50.dp)
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Start history game",
                modifier = Modifier.size(30.dp)
            )
        }
        Column(
            modifier = Modifier
                .width(75.dp)
                .padding(start = 5.dp, end = 5.dp)
        ) {
            Text(
                text = playerWithScores[0].getPlayerName(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = playerWithScores[0].getScoreString(),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        Divider(
            color = Color.Gray,
            modifier = Modifier
                .height(47.dp)
                .width(1.dp)
        )
        Column(
            modifier = Modifier
                .width(75.dp)
                .padding(start = 5.dp, end = 5.dp)
        ) {
            Text(
                text = playerWithScores[1].getPlayerName(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = playerWithScores[1].getScoreString(),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        Divider(
            color = Color.Gray,
            modifier = Modifier
                .height(47.dp)
                .width(1.dp)
        )
        Column(
            modifier = Modifier
                .width(75.dp)
                .padding(start = 5.dp, end = 5.dp)
        ) {
            Text(
                text = playerWithScores[2].getPlayerName(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = playerWithScores[2].getScoreString(),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        val date ="${game.skatGame.lastPlayed.dayOfMonth}." +
                "${shortenMonth(game.skatGame.lastPlayed.month)} " +
                "${game.skatGame.lastPlayed.year}"
        Text(
            text = date,
            fontSize = 2.5.em,
            modifier = Modifier.padding(end = 10.dp)
        )
    }
}

private fun shortenMonth(month: Month) : String {
    return when(month) {
        Month.JANUARY -> "Jan"
        Month.FEBRUARY -> "Feb"
        Month.MARCH -> "Mar"
        Month.APRIL -> "Apr"
        Month.MAY -> "May"
        Month.JUNE -> "Jun"
        Month.JULY -> "Jul"
        Month.AUGUST -> "Aug"
        Month.SEPTEMBER -> "Sep"
        Month.OCTOBER -> "Oct"
        Month.NOVEMBER -> "Nov"
        Month.DECEMBER -> "Dec"
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

@Preview
@Composable
fun PreviewPlayerInsert() {
    PlayerInsert(player = Player("dfdf"), onClick = {}, onRemove = {})
}

@Preview
@Composable
fun PreviewHistoryGamePreview() {
    val game = SampleRepository().getSkatGameWithScores()
    HistoryGamePreview(game = game, onClick = {})
}