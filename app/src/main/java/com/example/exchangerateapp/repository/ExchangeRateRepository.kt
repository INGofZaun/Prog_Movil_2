package com.example.exchangerateapp.repository

import com.example.exchangerateapp.data.ExchangeRate
import com.example.exchangerateapp.data.ExchangeRateDao

class ExchangeRateRepository(private val exchangeRateDao: ExchangeRateDao) {

    suspend fun insertRates(rates: List<ExchangeRate>) {
        exchangeRateDao.insertRates(rates)
    }

    suspend fun getRatesByDate(date: String): List<ExchangeRate> {
        return exchangeRateDao.getRatesByDate(date)
    }

    suspend fun getAvailableDates(): List<String> {
        return exchangeRateDao.getAvailableDates()
    }
}
