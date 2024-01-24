package com.example.skatcalculator.database.events

import com.example.skatcalculator.database.tables.Score
import com.example.skatcalculator.database.tables.SkatGame
import com.example.skatcalculator.database.tables.SpecialRounds
import com.example.skatcalculator.enums.SpecialRound

sealed interface SkatGameEvent {

    data class setSkatGameId(val skatGameId: String) : SkatGameEvent
    data class saveSkatGame(val skatGame: SkatGame) : SkatGameEvent
    data class deleteSkatGame(val skatGame: SkatGame) : SkatGameEvent
    data class saveScore(val score: Score) : SkatGameEvent
    data class saveSpecialRounds(val specialRounds: SpecialRounds) : SkatGameEvent

}