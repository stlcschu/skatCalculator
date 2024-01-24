package com.example.skatcalculator.database.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.skatcalculator.enums.SpecialRound

@Entity
data class SpecialRounds(
    val specialRounds: List<SpecialRound> = emptyList(),
    @PrimaryKey(autoGenerate = false)
    val gameId: String = ""
)
