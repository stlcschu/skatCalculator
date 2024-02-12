package com.example.skatcalculator.database.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Player(
    val name: String,
    @PrimaryKey(autoGenerate = false)
    val playerId: String = "NA"
) {

    fun equalsDefault() : Boolean {
        return name.isBlank() && name.isEmpty() && playerId == "NA"
    }

    fun equalsPlayer(player: Player) : Boolean {
        return name == player.name && playerId == player.playerId
    }

}
