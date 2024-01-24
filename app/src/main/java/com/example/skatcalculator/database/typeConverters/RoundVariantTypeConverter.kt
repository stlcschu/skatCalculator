package com.example.skatcalculator.database.typeConverters

import androidx.room.TypeConverter
import com.example.skatcalculator.enums.RoundVariant

class RoundVariantTypeConverter {

    @TypeConverter
    fun roundVariantToString(roundVariant: RoundVariant) : String {
        return roundVariant.value
    }

    @TypeConverter
    fun roundVariantFromString(roundVariantString: String) : RoundVariant {
        return RoundVariant.fromString(roundVariantString)
    }

}