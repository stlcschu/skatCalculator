package com.example.skatcalculator.util

class SeedGenerator {
    fun generateRandomSeed() : Long {
        return System.nanoTime()
    }
}