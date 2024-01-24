package com.example.skatcalculator.database.tables

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.LocalDate

@Entity
data class SkatGame (
    val playerOne: Player = Player(""),
    val playerTwo: Player = Player(""),
    val playerThree: Player = Player(""),
    val lastPlayed: LocalDate = LocalDate.now(),
    @PrimaryKey(autoGenerate = false)
    val skatGameId: String = ""
) {

    fun getPlayers() : List<Player> {
        return listOf(
            playerOne,
            playerTwo,
            playerThree
        )
    }
}


data class SkatGameWithRounds(
    @Embedded
    val skatGame: SkatGame = SkatGame(),
    @Relation(
        parentColumn = "skatGameId",
        entityColumn = "skatGameId"
    )
    val rounds: List<SkatRound> = emptyList()
)

data class SkatGameWithScores(
    @Embedded
    val skatGame: SkatGame = SkatGame(),
    @Relation(
        parentColumn = "skatGameId",
        entityColumn = "skatGameId"
    )
    val scores: List<Score> = emptyList()
)

data class SkatGameWithRoundsAndScores(
    @Embedded
    val skatGame: SkatGame = SkatGame(),
    @Relation(
        parentColumn = "skatGameId",
        entityColumn = "skatGameId"
    )
    val rounds: List<SkatRound> = emptyList(),
    @Relation(
        parentColumn = "skatGameId",
        entityColumn = "skatGameId"
    )
    val scores: List<Score> = emptyList()
)