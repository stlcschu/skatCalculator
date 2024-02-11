package com.example.skatcalculator.database.events

import com.example.skatcalculator.database.tables.Score
import com.example.skatcalculator.database.tables.SkatGame
import com.example.skatcalculator.database.tables.SpecialRounds

sealed interface SkatGameEvent {

    data class SetSkatGameId(val skatGameId: String) : SkatGameEvent
    data class SaveSkatGame(val skatGame: SkatGame) : SkatGameEvent
    data class DeleteSkatGame(val skatGame: SkatGame) : SkatGameEvent
    data class SaveScore(val score: Score) : SkatGameEvent
    data class SaveSpecialRounds(val specialRounds: SpecialRounds) : SkatGameEvent

}