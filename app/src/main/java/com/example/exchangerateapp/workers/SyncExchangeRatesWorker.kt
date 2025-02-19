package com.example.exchangerateapp.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.exchangerateapp.api.RetrofitInstance
import com.example.exchangerateapp.data.ExchangeRate
import com.example.exchangerateapp.data.ExchangeRateDatabase
import com.example.exchangerateapp.data.ExchangeRateDao  // ✅ Importamos correctamente
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class SyncExchangeRatesWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            Log.d("SyncWorker", "🔄 Iniciando sincronización de datos...")

            // Obtiene los datos de la API
            val response = RetrofitInstance.fetchExchangeRates()

            Log.d("SyncWorker", "✅ Datos obtenidos de la API: Base: ${response.base_code}, Fecha: ${response.time_last_update_utc}")

            // Mapea la respuesta a objetos ExchangeRate
            val rates = response.conversion_rates.map { (currency, rate) ->
                ExchangeRate(
                    date = response.time_last_update_utc,
                    baseCurrency = response.base_code,
                    targetCurrency = currency,
                    rate = rate
                )
            }

            Log.d("SyncWorker", "📌 ${rates.size} tipos de cambio convertidos.")

            // Inserta los datos en la base de datos
            val database = ExchangeRateDatabase.getDatabase(applicationContext)
            val dao: ExchangeRateDao = database.exchangeRateDao()  // ✅ Corregido

            insertRatesInDatabase(dao, rates)  // ✅ Corregido

            Log.d("SyncWorker", "✅ Datos insertados en la base de datos correctamente")

            Result.success()
        } catch (e: HttpException) {
            Log.e("SyncWorker", "❌ Error HTTP: ${e.code()} - ${e.message()}", e)
            Result.retry() // Reintentar en caso de error HTTP
        } catch (e: Exception) {
            Log.e("SyncWorker", "❌ Error general: ${e.message}", e)
            Result.failure()
        }
    }

    private suspend fun insertRatesInDatabase(dao: ExchangeRateDao, rates: List<ExchangeRate>) {
        withContext(Dispatchers.IO) {
            dao.insertRates(rates)
            Log.d("SyncWorker", "📥 ${rates.size} registros insertados en SQLite.")
        }
    }
}
