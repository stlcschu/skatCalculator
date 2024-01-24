package com.example.skatcalculator.util

import kotlin.random.Random

class IdGenerator() {

    private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun generatePlayerId(seed : Long = SeedGenerator().generateRandomSeed()) : String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("P_")
        stringBuilder.append(generateIdString(seed))
        return stringBuilder.toString()
    }

    fun generateGameId(seed: Long = SeedGenerator().generateRandomSeed()) : String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("SG_")
        stringBuilder.append(generateIdString(seed))
        return stringBuilder.toString()
    }

    private fun generateIdString(seed : Long) : String {
        val random = Random(seed)
        return (1..defaultIdLength).map { random.nextInt(0, charPool.size).let {charPool[it]} }.joinToString("")
    }

    companion object {
        const val defaultIdLength = 50
    }

}