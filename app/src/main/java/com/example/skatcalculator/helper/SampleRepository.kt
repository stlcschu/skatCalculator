package com.example.skatcalculator.helper

import com.example.skatcalculator.database.tables.Player
import com.example.skatcalculator.database.tables.Score
import com.example.skatcalculator.database.tables.SkatGame
import com.example.skatcalculator.database.tables.SkatGameWithScores
import com.example.skatcalculator.database.tables.SkatRound
import com.example.skatcalculator.dataclasses.PlayerWithScore
import com.example.skatcalculator.dataclasses.RoundModifier
import com.example.skatcalculator.enums.Declaration
import com.example.skatcalculator.enums.RoundType
import com.example.skatcalculator.enums.RoundVariant
import com.example.skatcalculator.enums.TrickColor
import java.time.LocalDate

class SampleRepository {

    fun getPlayerData() = listOf(
        Player("Anna"),
        Player("Florianen"),
        Player("Luca")
    )

    fun getSkatRound() = SkatRound(
        roundIndex = 1200,
        pointsGainedPlayerOne = 0,
        roundModifier = RoundModifier(
            declarations = listOf(
                Declaration.RE,
                Declaration.OUVERT,
                Declaration.HAND
            ),
            roundOutcomes = emptyList()
        ),
        isBockRound = false,
        trickColor = TrickColor.HEARTS,
        roundType = RoundType.WITH_WITHOUT_FOUR,
        roundVariant = RoundVariant.GRAND,
        skatGameId = ""
    )

    private fun getSkatGame() = SkatGame(
        playerOne = Player("test12ddddddddddddddddddddddddddddddddddddddddd", playerId = "player1"),
        playerTwo = Player("dfffffffffffffffd", playerId = "player2"),
        playerThree = Player("fdfdas", playerId = "player3"),
        lastPlayed = LocalDate.of(2020, 9, 10),
    )

    fun getSkatGameWithScores() = SkatGameWithScores(
        skatGame = getSkatGame(),
        scores = listOf(
            Score(
                score = 0,
                playerId = "player1"
            ),
            Score(
                score = 0,
                playerId = "player2"
            ),
            Score(
                score = 0,
                playerId = "player3"
            )
        )
    )

    fun getPlayerWithScore() = PlayerWithScore(
        Player("Anna"),
        Score(
            score = 0
        )
    )

    fun getPlayersWithScore () = listOf(
        getPlayerWithScore(),
        PlayerWithScore(
            Player("Florionae"),
            Score(
                score = 0
            )
        ),
        PlayerWithScore(
            Player("dsfsdfsfsfsfs"),
            Score(
                score = 0
            )
        )
    )
}