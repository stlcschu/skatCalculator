package com.example.skatcalculator.states

import com.example.skatcalculator.database.tables.Player

data class SkatGameState(
    val skatGameId: String = "",
    val playerOne: Player = Player(""),
    val playerTwo: Player = Player(""),
    val playerThree: Player = Player("")
)