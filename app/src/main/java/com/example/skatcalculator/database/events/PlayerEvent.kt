package com.example.skatcalculator.database.events

import com.example.skatcalculator.database.tables.Player

sealed interface PlayerEvent {

    data class SavePlayer(val player: Player) : PlayerEvent

    data class DeletePlayer(val player: Player) : PlayerEvent

}