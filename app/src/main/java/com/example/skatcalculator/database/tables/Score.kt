package com.example.skatcalculator.database.tables

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Score(
    val score: Int = 0,
    val skatGameId: String = "",
    val playerId: String = "NA",
    @PrimaryKey(autoGenerate = true)
    val scoreId: Int = 0
)
