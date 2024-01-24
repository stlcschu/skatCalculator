package com.example.skatcalculator.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.skatcalculator.database.tables.SpecialRounds
import kotlinx.coroutines.flow.Flow

@Dao
interface SpecialRoundsDao {

    @Transaction
    @Query("SELECT * FROM SpecialRounds WHERE gameId = :gameId")
    fun getSpecialRounds(gameId: String) : Flow<SpecialRounds>

    @Upsert
    suspend fun upsertSpecialRounds(specialRounds: SpecialRounds)

}