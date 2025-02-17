package com.example.exchangerateapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExchangeRateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(rates: List<ExchangeRate>) // Insertar o actualizar tasas de cambio

    @Query("SELECT * FROM exchange_rates WHERE date = :date")
    suspend fun getRatesByDate(date: String): List<ExchangeRate> // Obtener tasas por fecha

    @Query("SELECT DISTINCT date FROM exchange_rates ORDER BY date DESC")
    suspend fun getAvailableDates(): List<String> // Obtener todas las fechas disponibles
}
