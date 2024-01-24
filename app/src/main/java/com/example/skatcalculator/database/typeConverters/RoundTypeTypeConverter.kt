package com.example.skatcalculator.database.typeConverters

import androidx.room.TypeConverter
import com.example.skatcalculator.enums.RoundType

class RoundTypeTypeConverter {


    @TypeConverter
    fun roundTypeToString(roundType: RoundType) : String {
        return roundType.type
    }

    @TypeConverter
    fun roundTypeFromString(roundTypeString: String) : RoundType {
        return RoundType.fromString(roundTypeString)
    }
}