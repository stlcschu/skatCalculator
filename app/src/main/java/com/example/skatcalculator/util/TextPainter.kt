package com.example.skatcalculator.util

import androidx.compose.ui.graphics.Color

class TextPainter(private val paintValue: PaintValue) {

    fun getColor() : Color {
        when(paintValue) {
            PaintValue.RED -> {
                return Color.Red
            }
            PaintValue.GREEN -> {
                return Color.Green
            }
            PaintValue.HIGHLIGHT -> {
                return Color.Magenta
            }
            else -> {
                return Color.Black
            }
        }
    }

    companion object {
        enum class PaintValue() {
            RED,
            GREEN,
            HIGHLIGHT,
            NONE
        }
    }
}