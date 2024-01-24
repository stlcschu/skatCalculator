package com.example.skatcalculator.util

import com.example.skatcalculator.database.tables.Player
import com.example.skatcalculator.database.tables.Score
import com.example.skatcalculator.dataclasses.PlayerWithScore

class GroupPlayerWithScore(
    private val players: List<Player>,
    private val scores: List<Score>
) {

    fun group() : List<PlayerWithScore> {
        val playersWithScore = mutableListOf<PlayerWithScore>()

        for(player in players) {
            for(score in scores) {
                if (score.playerId != player.playerId) continue
                playersWithScore.add(PlayerWithScore(player, score))
                break
            }
        }

        return playersWithScore
    }

}