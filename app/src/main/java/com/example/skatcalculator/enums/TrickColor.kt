package com.example.skatcalculator.enums

import android.graphics.drawable.Drawable
import com.example.skatcalculator.R

enum class TrickColor(val value: String, val drawableId: Int, val baseValue: Int) {
    CROSSES("Crosses", R.drawable.symbol_cross, 12),
    SPADES("Spades", R.drawable.symbol_spade, 11),
    HEARTS("Hearts", R.drawable.symbol_heart, 10),
    DIAMONDS("Diamonds", R.drawable.symbol_diamond, 9);

    companion object {
        fun fromString(value: String) = entries.firstOrNull() { it.value == value } ?: throw IllegalArgumentException("Invalid type requested: $value")
        fun fromInt(value: Int) = entries.firstOrNull() { it.baseValue == value } ?: throw IllegalArgumentException("Invalid type requested: $value")

    }
}