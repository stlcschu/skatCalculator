package com.example.skatcalculator.helper

import com.example.skatcalculator.database.tables.Player

class SampleRepository() {

    fun getPlayerData() : List<Player> {
        return listOf<Player>(
            Player("Anna"),
            Player("Test")
        )
    }
}