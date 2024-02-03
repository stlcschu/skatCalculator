package com.example.skatcalculator.util

import com.example.skatcalculator.R
import kotlin.random.Random

class CardIconProvider {

    private val availableIcons: List<Int> = ICONS

    fun getAllIcons() = ICONS

    fun cardIconReturned(icon: Int) = availableIcons.toMutableList().add(icon)

    fun getAvailableIcon(seed: Long = SeedGenerator().generateRandomSeed()) : Int {
        val random = Random(seed)
        val selectedIconIndex = random.nextInt(0,availableIcons.size)
        val selectedIcon = availableIcons[selectedIconIndex]
        availableIcons.toMutableList().removeAt(selectedIconIndex)
        return selectedIcon
    }

    fun getIconsForLoadingAnimation(seed: Long = SeedGenerator().generateRandomSeed()) : List<Int> {
        val random = Random(seed)
        val selectedIcons = mutableListOf<Int>()
        for(i in 0..5) {
            val selectedIconIndex = random.nextInt(0, ICONS_BG_COLORED.size)
            selectedIcons.add(ICONS_BG_COLORED[selectedIconIndex])
        }
        return selectedIcons
    }

    companion object {
        private val ICONS: List<Int> = listOf(
            R.drawable.card_icon_crosses_ace,
            R.drawable.card_icon_crosses_1,
            R.drawable.card_icon_crosses_2,
            R.drawable.card_icon_crosses_3,
            R.drawable.card_icon_crosses_4,
            R.drawable.card_icon_crosses_5,
            R.drawable.card_icon_crosses_6,
            R.drawable.card_icon_crosses_7,
            R.drawable.card_icon_crosses_8,
            R.drawable.card_icon_crosses_9,
            R.drawable.card_icon_crosses_10,
            R.drawable.card_icon_crosses_j,
            R.drawable.card_icon_crosses_q,
            R.drawable.card_icon_crosses_k,
            R.drawable.card_icon_spades_ace,
            R.drawable.card_icon_spades_1,
            R.drawable.card_icon_spades_2,
            R.drawable.card_icon_spades_3,
            R.drawable.card_icon_spades_4,
            R.drawable.card_icon_spades_5,
            R.drawable.card_icon_spades_6,
            R.drawable.card_icon_spades_7,
            R.drawable.card_icon_spades_8,
            R.drawable.card_icon_spades_9,
            R.drawable.card_icon_spades_10,
            R.drawable.card_icon_spades_j,
            R.drawable.card_icon_spades_q,
            R.drawable.card_icon_spades_k,
            R.drawable.card_icon_hearts_ace,
            R.drawable.card_icon_hearts_1,
            R.drawable.card_icon_hearts_2,
            R.drawable.card_icon_hearts_3,
            R.drawable.card_icon_hearts_4,
            R.drawable.card_icon_hearts_5,
            R.drawable.card_icon_hearts_6,
            R.drawable.card_icon_hearts_7,
            R.drawable.card_icon_hearts_8,
            R.drawable.card_icon_hearts_9,
            R.drawable.card_icon_hearts_10,
            R.drawable.card_icon_hearts_j,
            R.drawable.card_icon_hearts_q,
            R.drawable.card_icon_hearts_k,
            R.drawable.card_icon_diamonds_ace,
            R.drawable.card_icon_diamonds_1,
            R.drawable.card_icon_diamonds_2,
            R.drawable.card_icon_diamonds_3,
            R.drawable.card_icon_diamonds_4,
            R.drawable.card_icon_diamonds_5,
            R.drawable.card_icon_diamonds_6,
            R.drawable.card_icon_diamonds_7,
            R.drawable.card_icon_diamonds_8,
            R.drawable.card_icon_diamonds_9,
            R.drawable.card_icon_diamonds_10,
            R.drawable.card_icon_diamonds_j,
            R.drawable.card_icon_diamonds_q,
            R.drawable.card_icon_diamonds_k
        )

        private val ICONS_BG_COLORED: List<Int> = listOf(
            R.drawable.card_icon_crosses_bgcolored_ace,
            R.drawable.card_icon_crosses_bgcolored_1,
            R.drawable.card_icon_crosses_bgcolored_2,
            R.drawable.card_icon_crosses_bgcolored_3,
            R.drawable.card_icon_crosses_bgcolored_4,
            R.drawable.card_icon_crosses_bgcolored_5,
            R.drawable.card_icon_crosses_bgcolored_6,
            R.drawable.card_icon_crosses_bgcolored_7,
            R.drawable.card_icon_crosses_bgcolored_8,
            R.drawable.card_icon_crosses_bgcolored_9,
            R.drawable.card_icon_crosses_bgcolored_10,
            R.drawable.card_icon_crosses_bgcolored_j,
            R.drawable.card_icon_crosses_bgcolored_q,
            R.drawable.card_icon_crosses_bgcolored_k,
            R.drawable.card_icon_spades_bgcolored_ace,
            R.drawable.card_icon_spades_bgcolored_1,
            R.drawable.card_icon_spades_bgcolored_2,
            R.drawable.card_icon_spades_bgcolored_3,
            R.drawable.card_icon_spades_bgcolored_4,
            R.drawable.card_icon_spades_bgcolored_5,
            R.drawable.card_icon_spades_bgcolored_6,
            R.drawable.card_icon_spades_bgcolored_7,
            R.drawable.card_icon_spades_bgcolored_8,
            R.drawable.card_icon_spades_bgcolored_9,
            R.drawable.card_icon_spades_bgcolored_10,
            R.drawable.card_icon_spades_bgcolored_j,
            R.drawable.card_icon_spades_bgcolored_q,
            R.drawable.card_icon_spades_bgcolored_k,
            R.drawable.card_icon_hearts_bgcolored_ace,
            R.drawable.card_icon_hearts_bgcolored_1,
            R.drawable.card_icon_hearts_bgcolored_2,
            R.drawable.card_icon_hearts_bgcolored_3,
            R.drawable.card_icon_hearts_bgcolored_4,
            R.drawable.card_icon_hearts_bgcolored_5,
            R.drawable.card_icon_hearts_bgcolored_6,
            R.drawable.card_icon_hearts_bgcolored_7,
            R.drawable.card_icon_hearts_bgcolored_8,
            R.drawable.card_icon_hearts_bgcolored_9,
            R.drawable.card_icon_hearts_bgcolored_10,
            R.drawable.card_icon_hearts_bgcolored_j,
            R.drawable.card_icon_hearts_bgcolored_q,
            R.drawable.card_icon_hearts_bgcolored_k,
            R.drawable.card_icon_diamonds_bgcolored_ace,
            R.drawable.card_icon_diamonds_bgcolored_1,
            R.drawable.card_icon_diamonds_bgcolored_2,
            R.drawable.card_icon_diamonds_bgcolored_3,
            R.drawable.card_icon_diamonds_bgcolored_4,
            R.drawable.card_icon_diamonds_bgcolored_5,
            R.drawable.card_icon_diamonds_bgcolored_6,
            R.drawable.card_icon_diamonds_bgcolored_7,
            R.drawable.card_icon_diamonds_bgcolored_8,
            R.drawable.card_icon_diamonds_bgcolored_9,
            R.drawable.card_icon_diamonds_bgcolored_10,
            R.drawable.card_icon_diamonds_bgcolored_j,
            R.drawable.card_icon_diamonds_bgcolored_q,
            R.drawable.card_icon_diamonds_bgcolored_k
        )
    }

}