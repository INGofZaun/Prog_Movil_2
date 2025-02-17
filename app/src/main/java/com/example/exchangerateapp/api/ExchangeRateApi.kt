package com.example.exchangerateapp.api

import com.example.exchangerateapp.data.ExchangeRate
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateApi {
    // Endpoint para obtener los tipos de cambio
    @GET("v4/latest")
    suspend fun getExchangeRates(
        @Query("base") baseCurrency: String = "USD" // Moneda base por defecto (USD)
    ): ExchangeRateResponse
}

data class ExchangeRateResponse(
    val base: String,
    val date: String,
    val rates: Map<String, Double> // Mapa de tasas de cambio
)
