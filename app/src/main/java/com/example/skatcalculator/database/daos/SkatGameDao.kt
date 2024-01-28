package com.example.skatcalculator.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.skatcalculator.database.tables.SkatGame
import com.example.skatcalculator.database.tables.SkatGameWithRoundsAndScores
import com.example.skatcalculator.database.tables.SkatGameWithScores
import kotlinx.coroutines.flow.Flow

@Dao
interface SkatGameDao {

    @Transaction
    @Query("SELECT * FROM SkatGame")
    fun getAllSkatGames() : Flow<List<SkatGame>>

    @Transaction
    @Query("SELECT * FROM SkatGame WHERE skatGameId = :id")
    fun getSkatGame(id: String) : Flow<SkatGameWithRoundsAndScores>

    @Transaction
    @Query("SELECT * FROM SkatGame ORDER BY lastPlayed LIMIT 5")
    fun getRecentSkatGames() : Flow<List<SkatGameWithScores>>

    @Upsert
    suspend fun upsertSkatGame(skatGame: SkatGame)

    @Delete
    suspend fun deleteSkatGame(skatGame: SkatGame)

}