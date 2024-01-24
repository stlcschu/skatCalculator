package com.example.skatcalculator.enums

enum class RoundVariant(val value: String) {
    NORMAL("Normal"),
    RAMSCH("Ramsch"),
    GRAND("Grand"),
    NULLSPIEL("Nullspiel");

    companion object {
        fun fromString(value: String) = entries.firstOrNull() { it.value == value } ?: throw IllegalArgumentException("Invalid type requested: $value")
    }

}