package com.example.skatcalculator.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.skatcalculator.composables.MainMenuScreen
import com.example.skatcalculator.database.AppDatabase
import com.example.skatcalculator.database.viewModels.PlayerViewModel
import com.example.skatcalculator.database.viewModels.PlayerViewModelFactory
import com.example.skatcalculator.database.viewModels.SkatGameViewModel
import com.example.skatcalculator.database.viewModels.SkatGameViewModelFactory
import com.example.skatcalculator.database.viewModels.SkatRoundViewModel
import com.example.skatcalculator.database.viewModels.SkatRoundViewModelFactory
import com.example.skatcalculator.navigation.MainMenu
import com.example.skatcalculator.navigation.SkatDestination
import com.example.skatcalculator.navigation.SkatNavHost
import com.example.skatcalculator.navigation.skatScreens

class MainActivity : ComponentActivity() {

    private val database by lazy { AppDatabase.getDatabase(this) }

    private val playerViewModel: PlayerViewModel by viewModels {
        PlayerViewModelFactory(database.playerDao)
    }

    private val skatGameViewModel: SkatGameViewModel by viewModels {
        SkatGameViewModelFactory(database.skatGameDao, database.scoreDao, database.specialRoundsDao)
    }

    private val skatRoundViewModel: SkatRoundViewModel by viewModels {
        SkatRoundViewModelFactory(database.skatRoundDao)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SkatApp()
        }
    }

    @Composable
    fun SkatApp() {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen = skatScreens.find { it.route == currentDestination?.route } ?: MainMenu
        Scaffold() { innerPadding ->
            SkatNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                playerViewModel = playerViewModel,
                skatGameViewModel = skatGameViewModel,
                skatRoundViewModel = skatRoundViewModel
            )
        }
    }

}