package com.example.skatcalculator.database.typeConverters

import androidx.room.TypeConverter
import com.example.skatcalculator.dataclasses.RoundModifier
import com.google.gson.Gson

class RoundModifierTypeConverter {

    private val _gson = Gson()

    @TypeConverter
    fun roundModifierToString(roundModifier: RoundModifier) : String {
        return _gson.toJson(roundModifier)
    }

    @TypeConverter
    fun roundModifierFromString(roundModifierString: String) : RoundModifier {
        return _gson.fromJson(roundModifierString, RoundModifier::class.java)
    }

}