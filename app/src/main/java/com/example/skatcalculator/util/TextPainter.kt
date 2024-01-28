package com.example.skatcalculator.util

import androidx.compose.ui.graphics.Color

class TextPainter(private val paintValue: PaintValue) {

    fun getColor() : Color {
        return when(paintValue) {
            PaintValue.RED -> {
                Color.Red
            }

            PaintValue.GREEN -> {
                Color.Green
            }

            PaintValue.HIGHLIGHT -> {
                Color.Magenta
            }

            else -> {
                Color.Black
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