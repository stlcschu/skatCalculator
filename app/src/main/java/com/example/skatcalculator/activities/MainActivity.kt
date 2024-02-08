package com.example.skatcalculator.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.skatcalculator.database.AppDatabase
import com.example.skatcalculator.database.viewModels.PlayerViewModel
import com.example.skatcalculator.database.viewModels.PlayerViewModelFactory
import com.example.skatcalculator.database.viewModels.SkatGameViewModel
import com.example.skatcalculator.database.viewModels.SkatGameViewModelFactory
import com.example.skatcalculator.database.viewModels.SkatRoundViewModel
import com.example.skatcalculator.database.viewModels.SkatRoundViewModelFactory
import com.example.skatcalculator.navigation.SkatNavHost
import com.example.skatcalculator.util.CardIconProvider

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
        val cardIconProvider = CardIconProvider()
        Scaffold() { innerPadding ->
            SkatNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                cardIconProvider = cardIconProvider,
                playerViewModel = playerViewModel,
                skatGameViewModel = skatGameViewModel,
                skatRoundViewModel = skatRoundViewModel
            )
        }
    }

}