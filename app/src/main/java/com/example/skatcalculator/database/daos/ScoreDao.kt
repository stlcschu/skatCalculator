package com.example.skatcalculator.database.daos

import androidx.room.Dao
import androidx.room.Upsert
import com.example.skatcalculator.database.tables.Score

@Dao
interface ScoreDao {

    @Upsert
    suspend fun upsertScore(score: Score)

}