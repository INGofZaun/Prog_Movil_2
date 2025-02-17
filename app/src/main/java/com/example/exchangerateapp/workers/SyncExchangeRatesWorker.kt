package com.example.exchangerateapp.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.exchangerateapp.api.RetrofitInstance
import com.example.exchangerateapp.data.ExchangeRate
import com.example.exchangerateapp.data.ExchangeRateDatabase
import com.example.exchangerateapp.data.ExchangeRateDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.runBlocking

class SyncExchangeRatesWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        return try {
            // Utiliza runBlocking para permitir ejecutar funciones suspend
            runBlocking {
                // Obtén los datos de la API
                val response = RetrofitInstance.api.getExchangeRates()

                // Mapea la respuesta a objetos ExchangeRate
                val rates = response.rates.map { (currency, rate) ->
                    ExchangeRate(
                        date = response.date,
                        baseCurrency = response.base,
                        targetCurrency = currency,
                        rate = rate
                    )
                }

                // Inserta los datos en la base de datos
                val database = ExchangeRateDatabase.getDatabase(applicationContext)
                val dao = database.exchangeRateDao()

                // Usamos corutinas para insertar en la base de datos
                insertRatesInDatabase(dao, rates)
            }

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private suspend fun insertRatesInDatabase(dao: ExchangeRateDao, rates: List<ExchangeRate>) {
        withContext(Dispatchers.IO) {
            dao.insertRates(rates)
        }
    }
}
