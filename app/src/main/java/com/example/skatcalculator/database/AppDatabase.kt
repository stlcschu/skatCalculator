package com.example.skatcalculator.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.skatcalculator.database.daos.PlayerDao
import com.example.skatcalculator.database.daos.ScoreDao
import com.example.skatcalculator.database.daos.SkatGameDao
import com.example.skatcalculator.database.daos.SkatRoundDao
import com.example.skatcalculator.database.daos.SpecialRoundsDao
import com.example.skatcalculator.database.tables.Player
import com.example.skatcalculator.database.tables.Score
import com.example.skatcalculator.database.tables.SkatGame
import com.example.skatcalculator.database.tables.SkatRound
import com.example.skatcalculator.database.tables.SpecialRounds
import com.example.skatcalculator.database.typeConverters.IntListTypeConverter
import com.example.skatcalculator.database.typeConverters.LocalDateTypeConverter
import com.example.skatcalculator.database.typeConverters.PlayerTypeConverter
import com.example.skatcalculator.database.typeConverters.RoundModifierTypeConverter
import com.example.skatcalculator.database.typeConverters.RoundTypeTypeConverter
import com.example.skatcalculator.database.typeConverters.RoundVariantTypeConverter
import com.example.skatcalculator.database.typeConverters.SpecialRoundListTypeConverter
import com.example.skatcalculator.database.typeConverters.TrickColorTypeConverter

@Database(
    entities =[
        Player::class,
        SkatGame::class,
        SkatRound::class,
        Score::class,
        SpecialRounds::class
    ],
    version = 1
)
@TypeConverters(
    LocalDateTypeConverter::class,
    RoundModifierTypeConverter::class,
    RoundTypeTypeConverter::class,
    RoundVariantTypeConverter::class,
    TrickColorTypeConverter::class,
    PlayerTypeConverter::class,
    SpecialRoundListTypeConverter::class,
    IntListTypeConverter::class

)
abstract class AppDatabase : RoomDatabase() {

    abstract val playerDao : PlayerDao

    abstract val skatGameDao : SkatGameDao

    abstract val skatRoundDao : SkatRoundDao

    abstract val scoreDao : ScoreDao

    abstract val specialRoundsDao : SpecialRoundsDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }

}
