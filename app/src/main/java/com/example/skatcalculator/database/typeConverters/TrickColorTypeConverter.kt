package com.example.skatcalculator.database.typeConverters

import androidx.room.TypeConverter
import com.example.skatcalculator.enums.TrickColor

class TrickColorTypeConverter {

    @TypeConverter
    fun trickColorToString(trickColor: TrickColor) : String {
        return trickColor.value
    }

    @TypeConverter
    fun trickColorFromString(trickColorString: String) : TrickColor {
        return TrickColor.fromString(trickColorString)
    }

}