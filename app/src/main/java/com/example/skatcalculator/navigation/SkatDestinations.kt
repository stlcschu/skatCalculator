package com.example.skatcalculator.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.skatcalculator.composables.MainMenuScreen
import com.example.skatcalculator.composables.SkatGameScreen
import com.example.skatcalculator.database.tables.SkatGameWithRoundsAndScores
import com.example.skatcalculator.database.tables.SpecialRounds
import com.example.skatcalculator.states.SkatRoundInformationState
import com.example.skatcalculator.states.SkatRoundState

interface SkatDestination {
    val icon: ImageVector
    val route: String
    val screen: @Composable () -> Unit
}

object MainMenu : SkatDestination {
    override val icon: ImageVector
        get() = TODO("Not yet implemented")
    override val route: String
        get() = "mainMenu"
    override val screen: @Composable () -> Unit
        get() = { MainMenuScreen(players = emptyList(), historyGames = emptyList(), onPlayerEvent = {}, onSkatGameEvent = {}) }
}

object SkatGame : SkatDestination {
    override val icon: ImageVector
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

val skatScreens = listOf<SkatDestination>(MainMenu, SkatGame)