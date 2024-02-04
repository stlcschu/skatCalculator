package com.example.skatcalculator.enums

enum class Declaration(val value: String, val multiplier: Int) {
    RE("Re", 2),
    KONTRA("Kontra", 2),
    SCHNEIDER("Schneider", 2),
    SCHWARZ("Schwarz", 2),
    OUVER("Ouvert", 0),
    HAND("Hand", 0);

    companion object {
        fun fromString(value: String) = entries.firstOrNull() { it.value == value } ?: throw IllegalArgumentException("Invalid type requested: $value")
    }
}