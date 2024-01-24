package com.example.skatcalculator.database.typeConverters

import androidx.room.TypeConverter
import java.time.LocalDate

class LocalDateTypeConverter {

    @TypeConverter
    fun localDateToString(date: LocalDate) : String {
        return "${date.year}.${date.monthValue}.${date.dayOfMonth}"
    }

    @TypeConverter
    fun localDateFromString(dateString: String) : LocalDate {
        val dateValues = dateString.split(".")
        return LocalDate.of(dateValues[0].toInt(), dateValues[1].toInt(), dateValues[2].toInt())
    }

}