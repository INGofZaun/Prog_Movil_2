package com.example.exchangerateapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ExchangeRate::class], version = 1, exportSchema = false)
abstract class ExchangeRateDatabase : RoomDatabase() {

    abstract fun exchangeRateDao(): ExchangeRateDao

    companion object {
        @Volatile
        private var INSTANCE: ExchangeRateDatabase? = null

        fun getDatabase(context: Context): ExchangeRateDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExchangeRateDatabase::class.java,
                    "exchange_rate_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
