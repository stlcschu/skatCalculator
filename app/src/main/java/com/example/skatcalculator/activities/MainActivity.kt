package com.example.skatcalculator.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.skatcalculator.R
import com.example.skatcalculator.database.AppDatabase
import com.example.skatcalculator.database.viewModels.PlayerViewModel
import com.example.skatcalculator.database.viewModels.PlayerViewModelFactory
import com.example.skatcalculator.database.viewModels.SkatGameViewModel
import com.example.skatcalculator.database.viewModels.SkatGameViewModelFactory
import com.example.skatcalculator.database.viewModels.SkatRoundViewModel
import com.example.skatcalculator.database.viewModels.SkatRoundViewModelFactory
import com.example.skatcalculator.navigation.SkatGame
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SkatApp() {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val cardIconProvider = CardIconProvider()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = colorResource(id = R.color.Lavender_web)
                    ),
                    title = {
                        Text(
                            "Skat App",
                            maxLines = 1,
                        )
                    },
                    navigationIcon = {
                        when(val currentRoute = navBackStackEntry?.destination?.route) {
                            SkatGame.route -> {
                                IconButton(
                                    onClick = {
                                        navController.navigateUp()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Localized description",
                                        tint = Color.Black
                                    )
                                }
                            }
                        }
                    }
                )
            }
        ) { innerPadding ->
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