package com.example.skatcalculator.database.typeConverters

import androidx.room.TypeConverter
import com.example.skatcalculator.enums.SpecialRound

class SpecialRoundListTypeConverter {

    @TypeConverter
    fun specialRoundListToString(specialRounds: List<SpecialRound>) : String {
        val stringBuilder = StringBuilder()
        specialRounds.forEachIndexed { index, specialRound ->
            if (index == specialRounds.size - 1) {
                stringBuilder.append(specialRound.value)
            } else {
                stringBuilder.append(specialRound.value)
                stringBuilder.append(SEPARATOR)
            }
        }
        specialRounds.toString()
        return stringBuilder.toString()
    }

    @TypeConverter
    fun specialRoundListFromString(specialRoundListString: String) : List<SpecialRound> {
        if (specialRoundListString.isEmpty() || specialRoundListString.isBlank()) return emptyList()
        val splits = specialRoundListString.split(SEPARATOR)
        val specialRounds = mutableListOf<SpecialRound>()
        for (split in splits) {
            if (split.isBlank() || split.isEmpty()) continue
            specialRounds.add(SpecialRound.fromString(split))
        }
        return specialRounds
    }

    companion object {
        const val SEPARATOR = "|"
    }

}