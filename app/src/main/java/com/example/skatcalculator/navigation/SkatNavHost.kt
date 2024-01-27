package com.example.skatcalculator.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.skatcalculator.composables.MainMenuScreen
import com.example.skatcalculator.composables.SkatGameScreen
import com.example.skatcalculator.database.viewModels.PlayerViewModel
import com.example.skatcalculator.database.viewModels.SkatGameViewModel
import com.example.skatcalculator.database.viewModels.SkatRoundViewModel

@Composable
fun SkatNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    playerViewModel: PlayerViewModel,
    skatGameViewModel: SkatGameViewModel,
    skatRoundViewModel: SkatRoundViewModel
) {
    NavHost(
        navController = navController,
        startDestination = MainMenu.route,
        modifier = modifier
    ) {
        composable(route = MainMenu.route) {
            val players = playerViewModel.state.collectAsState().value
            val historyGames = skatGameViewModel.historyGames.collectAsState().value
            MainMenuScreen(
                players = players,
                historyGames = historyGames,
                onPlayerEvent = playerViewModel::onEvent,
                onSkatGameEvent = skatGameViewModel::onEvent,
                onClickStartSkatGame = {
                    navController.navigateSingleTopTo(SkatGame.route)

                }
            )
        }
        composable(route = SkatGame.route) {
            val skatGame = skatGameViewModel.state.collectAsState().value
            val skatRoundState = skatRoundViewModel.state.collectAsState().value
            val specialRounds = skatGameViewModel.upcomingSpecialRounds.collectAsState().value
            val roundInformationState = skatRoundViewModel.roundInformationState.collectAsState().value
            SkatGameScreen(
                skatGame = skatGame,
                skatRoundState = skatRoundState,
                specialRoundsState = specialRounds,
                roundInformationState = roundInformationState,
                onSkatRoundEvent = skatRoundViewModel::onEvent,
                onSkatGameEvent = skatGameViewModel::onEvent
            )
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }