package com.example.skatcalculator.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.skatcalculator.database.tables.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {

    @Transaction
    @Query("SELECT * FROM Player")
    fun getAllPlayers() : Flow<List<Player>>

    @Transaction
    @Query("SELECT * FROM Player WHERE playerId = :id")
    fun getPlayerWithId(id: String) : Flow<Player>

    @Upsert
    suspend fun upsertPlayer(player: Player)

    @Delete
    suspend fun deletePlayer(player: Player)

}