package com.example.exchangerateapp.ui

import android.app.Application
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.exchangerateapp.provider.ExchangeRateProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ExchangeRateUI(
    val date: String,
    val baseCurrency: String,
    val targetCurrency: String,
    val rate: Double
)

class ExchangeRateViewModel(application: Application) : AndroidViewModel(application) {

    private val _exchangeRates = MutableStateFlow<List<ExchangeRateUI>>(emptyList())
    val exchangeRates: StateFlow<List<ExchangeRateUI>> = _exchangeRates

    init {
        loadExchangeRates()
    }

    private fun loadExchangeRates() {
        viewModelScope.launch {
            Log.e("ExchangeRateViewModel", "Cargando datos desde el ContentProvider...")

            val contentResolver = getApplication<Application>().contentResolver
            val uri = Uri.parse("content://${ExchangeRateProvider.AUTHORITY}/${ExchangeRateProvider.TABLE_NAME}")
            val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)

            cursor?.use {
                val list = mutableListOf<ExchangeRateUI>()
                while (it.moveToNext()) {
                    val date = it.getString(it.getColumnIndexOrThrow("date"))
                    val baseCurrency = it.getString(it.getColumnIndexOrThrow("baseCurrency"))
                    val targetCurrency = it.getString(it.getColumnIndexOrThrow("targetCurrency"))
                    val rate = it.getDouble(it.getColumnIndexOrThrow("rate"))

                    list.add(ExchangeRateUI(date, baseCurrency, targetCurrency, rate))
                }

                Log.e("ExchangeRateViewModel", "Datos obtenidos del ContentProvider: $list")
                _exchangeRates.value = list
            } ?: Log.e("ExchangeRateViewModel", "Cursor vacío, no se encontraron datos.")
        }
    }
}
