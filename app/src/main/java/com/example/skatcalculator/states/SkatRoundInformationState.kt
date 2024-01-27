package com.example.skatcalculator.states

import com.example.skatcalculator.database.tables.Player
import com.example.skatcalculator.database.tables.SkatRound

data class SkatRoundInformationState (
    val round: SkatRound = SkatRound(),
    val player: Player = Player("")
)