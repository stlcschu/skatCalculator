package com.example.skatcalculator.dataclasses

import com.example.skatcalculator.database.tables.Player
import com.example.skatcalculator.database.tables.Score

data class PlayerWithScore(
    val player: Player = Player(""),
    val score: Score = Score()
) {

    fun getPlayerName() : String {
        return player.name
    }

    fun getScoreString() : String {
        return score.score.toString()
    }

}
