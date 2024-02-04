package com.example.skatcalculator.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.window.Dialog
import com.example.skatcalculator.R
import com.example.skatcalculator.composables.defaults.DefaultCardClickable
import com.example.skatcalculator.composables.defaults.DefaultCardClickableWithButton
import com.example.skatcalculator.composables.defaults.DefaultLoadingAnimation
import com.example.skatcalculator.composables.defaults.LoadingCard
import com.example.skatcalculator.database.events.PlayerEvent
import com.example.skatcalculator.database.events.SkatGameEvent
import com.example.skatcalculator.database.tables.Player
import com.example.skatcalculator.database.tables.Score
import com.example.skatcalculator.database.tables.SkatGame
import com.example.skatcalculator.database.tables.SkatGameWithScores
import com.example.skatcalculator.database.tables.SpecialRounds
import com.example.skatcalculator.helper.SampleRepository
import com.example.skatcalculator.util.CardIconProvider
import com.example.skatcalculator.util.GroupPlayerWithScore
import com.example.skatcalculator.util.IdGenerator
import kotlinx.coroutines.delay
import java.time.Month

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainMenuScreen(
    players: List<Player>,
    historyGames: List<SkatGameWithScores>,
    cardIconProvider: CardIconProvider,
    menuBackground: Int,
    onPlayerEvent: (PlayerEvent) -> Unit,
    onSkatGameEvent: (SkatGameEvent) -> Unit,
    onClickStartSkatGame: () -> Unit = {}
) {
    val historyLazyListState = rememberLazyListState()
    Column(
//        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = Color.LightGray)
            .fillMaxHeight()
    ) {

        var showLoadingHistory by remember { mutableStateOf(true) }
        val loadingIcons = cardIconProvider.getIconsForLoadingAnimation()

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
                                if (player.name != playerOne.name && player.name != playerTwo.name && player.name != playerThree.name) {
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
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .fillMaxHeight()
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
        val topHeightFraction = if (historyLazyListState.firstVisibleItemIndex > 10) 0.1f else 0.5f
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(topHeightFraction),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = menuBackground),
                contentDescription = "Menu Background",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(
                        RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 20.dp,
                            bottomEnd = 20.dp
                        )
                    ),
                contentScale = ContentScale.Crop,
            )
            Card(
                backgroundColor = colorResource(id = R.color.Lavender_web).copy(alpha = 0.6f),
                contentColor = colorResource(id = R.color.philippine_silver),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            top = 20.dp,
                            bottom = 20.dp
                        ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var playerOneIcon by remember { mutableIntStateOf(cardIconProvider.getAvailableIcon()) }
                    PlayerInsert(
                        playerOne,
                        playerOneIcon,
                        onClick = {
                            addPlayerOnIndex = 1
                            showNewPlayerDialog = true
                        },
                        onRemove = {
                            playerOne = Player("")
                            cardIconProvider.cardIconReturned(playerOneIcon)
                            playerOneIcon = cardIconProvider.getAvailableIcon()
                        }
                    )
                    var playerTwoIcon by remember { mutableIntStateOf(cardIconProvider.getAvailableIcon()) }
                    PlayerInsert(
                        playerTwo,
                        playerTwoIcon,
                        onClick = {
                            addPlayerOnIndex = 2
                            showNewPlayerDialog = true
                        },
                        onRemove = {
                            playerTwo = Player("")
                            cardIconProvider.cardIconReturned(playerTwoIcon)
                            playerTwoIcon = cardIconProvider.getAvailableIcon()
                        }
                    )
                    var playerThreeIcon by remember { mutableIntStateOf(cardIconProvider.getAvailableIcon()) }
                    PlayerInsert(
                        playerThree,
                        playerThreeIcon,
                        onClick = {
                            addPlayerOnIndex = 3
                            showNewPlayerDialog = true
                        },
                        onRemove = {
                            playerThree = Player("")
                            cardIconProvider.cardIconReturned(playerThreeIcon)
                            playerThreeIcon = cardIconProvider.getAvailableIcon()
                        }
                    )
                    Spacer(
                        modifier = Modifier
                            .height(25.dp)
                    )
                }
            }

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
                enabled = gameIsReady,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                        bottom = 20.dp
                    )
            ) {
                Text(
                    text = "Start game",
                    color = colorResource(id = R.color.gunmetal)
                )
            }
        }

        if (historyGames.isEmpty() && showLoadingHistory) {
            DefaultLoadingAnimation(cardIcons = loadingIcons)
            return
        }
        if(historyGames.isEmpty()) {
            Text(text = "No Games found")
            return
        }
        LazyColumn(
            state = historyLazyListState
        ) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlayerInsert(
    player: Player,
    playerIcon: Int,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {

    if (player.isEmpty()) {
        DefaultCardClickable(
            header = "Player",
            onClick = onClick
        ) {
            Text(text = "Click to add player")
        }
        return
    }

    DefaultCardClickableWithButton(
        header = "Player",
        buttonIcon = R.drawable.baseline_remove_24,
        onClick = onClick,
        buttonFunction = onRemove
    ) {
        Icon(painter = painterResource(id = playerIcon), contentDescription = "Player icon")
        Text(
            text = player.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = colorResource(id = R.color.gunmetal)
        )
    }
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
        CardIconProvider(),
        R.drawable.menu_background_1,
        onPlayerEvent = {},
        onSkatGameEvent = {}
    )
}

@Preview
@Composable
fun PreviewPlayerInsert() {
    PlayerInsert(
        player = Player("tmp"),
        playerIcon = R.drawable.card_icon_crosses_2,
        onClick = {},
        onRemove = {}
    )
}

@Preview
@Composable
fun PreviewHistoryGamePreview() {
    val game = SampleRepository().getSkatGameWithScores()
    HistoryGamePreview(game = game, onClick = {})
}