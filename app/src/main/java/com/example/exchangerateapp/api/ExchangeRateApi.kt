package com.example.exchangerateapp.api

import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeRateApi {
    @GET("v6/{apiKey}/latest/USD")  // API Key en la URL
    suspend fun getExchangeRates(
        @Path("apiKey") apiKey: String  // Parámetro en la URL
    ): ExchangeRateResponse
}

data class ExchangeRateResponse(
    val base_code: String,  // La API usa "base_code", no "base"
    val time_last_update_utc: String, // La API usa este campo para la fecha
    val conversion_rates: Map<String, Double> // La API usa "conversion_rates"
)
