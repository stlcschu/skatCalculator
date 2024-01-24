package com.example.skatcalculator.database.events

import com.example.skatcalculator.database.tables.Player

sealed interface PlayerEvent {

    data class savePlayer(val player: Player) : PlayerEvent

    data class deletePlayer(val player: Player) : PlayerEvent

}