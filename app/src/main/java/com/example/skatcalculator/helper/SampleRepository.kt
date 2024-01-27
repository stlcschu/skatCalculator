package com.example.skatcalculator.helper

import com.example.skatcalculator.database.tables.Player
import com.example.skatcalculator.database.tables.SkatRound
import com.example.skatcalculator.dataclasses.RoundModifier
import com.example.skatcalculator.enums.Declaration
import com.example.skatcalculator.enums.RoundType
import com.example.skatcalculator.enums.RoundVariant
import com.example.skatcalculator.enums.TrickColor

class SampleRepository() {

    fun getPlayerData() = listOf<Player>(
        Player("Anna"),
        Player("Test")
    )

    fun getSkatRound() = SkatRound(
        roundIndex = 12,
        pointsGainedPlayerOne = 120,
        roundModifier = RoundModifier(
            declarations = listOf(
                Declaration.RE,
                Declaration.OUVER,
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
}