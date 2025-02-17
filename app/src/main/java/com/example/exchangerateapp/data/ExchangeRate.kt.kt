package com.example.exchangerateapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exchange_rates")
data class ExchangeRate(
    @PrimaryKey val date: String, // Fecha del tipo de cambio (YYYY-MM-DD)
    val baseCurrency: String,     // Moneda base (Ej: "USD")
    val targetCurrency: String,   // Moneda objetivo (Ej: "EUR")
    val rate: Double              // Tipo de cambio
)
