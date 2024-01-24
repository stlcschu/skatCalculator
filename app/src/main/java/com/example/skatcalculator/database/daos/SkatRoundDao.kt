package com.example.skatcalculator.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.skatcalculator.database.tables.SkatRound
import kotlinx.coroutines.flow.Flow

@Dao
interface SkatRoundDao {

    @Transaction
    @Query("SELECT * FROM SkatRound WHERE skatGameId = :skatGameId")
    fun getAllRoundForSkatGame(skatGameId: String) : Flow<List<SkatRound>>

    @Upsert
    suspend fun upsertRound(skatRound: SkatRound)

    @Delete
    suspend fun deleteRound(skatRound: SkatRound)

}