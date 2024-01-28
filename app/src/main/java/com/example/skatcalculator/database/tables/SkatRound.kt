package com.example.skatcalculator.database.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.skatcalculator.dataclasses.RoundModifier
import com.example.skatcalculator.enums.RoundVariant
import com.example.skatcalculator.enums.RoundType
import com.example.skatcalculator.enums.TrickColor

@Entity
data class SkatRound(
    val pointsGainedPlayerOne: Int = 0,
    val pointsGainedPlayerTwo: Int = 0,
    val pointsGainedPlayerThree: Int = 0,
    val roundModifier: RoundModifier = RoundModifier(emptyList(), emptyList()),
    val isBockRound: Boolean = false,
    val trickColor: TrickColor = TrickColor.CROSSES,
    val roundType: RoundType = RoundType.WITH_WITHOUT_ONE,
    val roundVariant: RoundVariant = RoundVariant.NORMAL,
    val roundIndex: Int = 0,
    val skatGameId: String = "",
    @PrimaryKey(autoGenerate = true)
    val skatRoundId: Int = 0
)
