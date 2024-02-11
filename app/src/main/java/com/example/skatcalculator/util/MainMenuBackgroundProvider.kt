package com.example.skatcalculator.util

import com.example.skatcalculator.R
import kotlin.random.Random

class MainMenuBackgroundProvider(seed: Long = SeedGenerator().generateRandomSeed()) {

    private var random: Random

    init {
        random = Random(seed)
    }

    fun getRandomBackGround() : Int {
        val selectedIconIndex = random.nextInt(0,BACKGROUNDS.size)
        return BACKGROUNDS[selectedIconIndex]
    }

    companion object {
        private val BACKGROUNDS = listOf(
            R.drawable.menu_background_1,
            R.drawable.menu_background_2,
            R.drawable.menu_background_3,
            R.drawable.menu_background_4,
            R.drawable.menu_background_5,
            R.drawable.menu_background_6,
            R.drawable.menu_background_7
        )
    }

}