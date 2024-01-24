package com.example.skatcalculator.database.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.skatcalculator.dataclasses.RoundModifier
import com.example.skatcalculator.enums.RoundVariant
import com.example.skatcalculator.enums.RoundType
import com.example.skatcalculator.enums.SpecialRound
import com.example.skatcalculator.enums.TrickColor

@Entity
data class SkatRound(
    val pointsGainedPlayerOne: Int = 0,
    val pointsGainedPlayerTwo: Int = 0,
    val pointsGainedPlayerThree: Int = 0,
    val roundModifier: RoundModifier,
    val isBockRound: Boolean,
    val trickColor: TrickColor,
    val roundType: RoundType,
    val roundVariant: RoundVariant,
    val roundIndex: Int,
    val skatGameId: String,
    @PrimaryKey(autoGenerate = true)
    val skatRoundId: Int = 0
)
