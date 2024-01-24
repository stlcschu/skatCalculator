package com.example.skatcalculator.enums

enum class SpecialRound(val value: String) {
    BOCK("Bock"),
    RAMSCH("Ramsch"),
    NONE("None");

    companion object {
        fun fromString(value: String) = entries.firstOrNull() { it.value == value } ?: throw IllegalArgumentException("Invalid type requested: $value")

    }
}