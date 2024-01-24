package com.example.skatcalculator.enums

enum class RoundType(val type: String, val value: Int) {

    WITH_WITHOUT_ONE("With/Without One", 2),
    WITH_WITHOUT_TWO("With/Without Two", 3),
    WITH_WITHOUT_THREE("With/Without Three", 4),
    WITH_WITHOUT_FOUR("With/Without Four", 5);

    companion object {
        fun fromInt(value: Int) = entries.firstOrNull() { it.value == value } ?: throw IllegalArgumentException("Invalid type requested: $value")
        fun fromString(value: String) = entries.firstOrNull() { it.type == value } ?: throw IllegalArgumentException("Invalid type requested: $value")
        fun toList() = listOf(WITH_WITHOUT_ONE, WITH_WITHOUT_TWO, WITH_WITHOUT_THREE, WITH_WITHOUT_FOUR)
    }

}