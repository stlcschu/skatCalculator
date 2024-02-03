package com.example.skatcalculator.enums

enum class RoundOutcome(val value: String, val modifier: Int) {
    DURCHMARSCH("Successful Durchmarsch", 2),
    JUNGFRAU("Jungfrau", 2),
    SUCCESSFUL_SCHNEIDER("Successful Schneider", 0),
    SUCCESSFUL_SCHWARZ("Successful Schwarz", 0)
}