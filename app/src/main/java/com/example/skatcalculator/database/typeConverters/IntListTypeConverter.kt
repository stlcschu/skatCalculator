package com.example.skatcalculator.database.typeConverters

import androidx.room.TypeConverter

class IntListTypeConverter {

    @TypeConverter
    fun intListToString(list: List<Int>) : String {
        val stringBuilder = StringBuilder()
        list.forEachIndexed { index, entry ->
            if (index == list.size - 1) {
                stringBuilder.append(entry.toString())
            } else {
                stringBuilder.append(entry.toString())
                stringBuilder.append(SEPARATOR)
            }
        }
        return stringBuilder.toString()
    }

    @TypeConverter
    fun intListFromString(listString: String) : List<Int> {
        if (listString.isEmpty() || listString.isBlank()) return emptyList()
        val newList = mutableListOf<Int>()
        val splits = listString.split(SEPARATOR)
        for (split in splits) {
            if (split.isBlank() || split.isEmpty()) continue
            newList.add(split.toInt())
        }
        return newList
    }

    companion object {
        const val SEPARATOR = "|"
    }

}