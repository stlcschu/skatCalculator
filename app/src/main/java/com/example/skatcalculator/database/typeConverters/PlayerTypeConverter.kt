package com.example.skatcalculator.database.typeConverters

import androidx.room.TypeConverter
import com.example.skatcalculator.database.tables.Player
import com.google.gson.Gson

class PlayerTypeConverter {

    private val _gson = Gson()

    @TypeConverter
    fun playerToString(player: Player) : String {
        return _gson.toJson(player)
    }

    @TypeConverter
    fun playerFromString(playerString: String) : Player {
        return _gson.fromJson(playerString, Player::class.java)
    }

}