package com.example.skatcalculator.navigation

import androidx.compose.runtime.Composable
import com.example.skatcalculator.composables.MainMenuScreen
import com.example.skatcalculator.composables.SkatGameScreen
import com.example.skatcalculator.database.tables.SkatGameWithRoundsAndScores
import com.example.skatcalculator.database.tables.SpecialRounds
import com.example.skatcalculator.states.SkatRoundInformationState
import com.example.skatcalculator.states.SkatRoundState
import com.example.skatcalculator.util.CardIconProvider
import com.example.skatcalculator.util.MainMenuBackgroundProvider

interface SkatDestination {
    val icon: Int
    val route: String
    val screen: @Composable () -> Unit
}

object MainMenu : SkatDestination {
    override val icon: Int
        get() = MainMenuBackgroundProvider().getRandomBackGround()
    override val route: String
        get() = "mainMenu"
    override val screen: @Composable () -> Unit
        get() = {
            MainMenuScreen(
                players = emptyList(),
                historyGames = emptyList(),
                cardIconProvider = CardIconProvider(),
                icon,
                onPlayerEvent = {},
                onSkatGameEvent = {}
            )
        }
}

object SkatGame : SkatDestination {
    override val icon: Int
        get() = TODO("Not yet implemented")
    override val route: String
        get() = "game"
    override val screen: @Composable () -> Unit
        get() = {
            SkatGameScreen(
                skatGame = SkatGameWithRoundsAndScores(),
                skatRoundState = SkatRoundState(),
                specialRoundsState = SpecialRounds(),
                roundInformationState = SkatRoundInformationState(),
                onSkatRoundEvent = {},
                onSkatGameEvent = {}
            )
        }
}

val skatScreens = listOf(MainMenu, SkatGame)