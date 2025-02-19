package com.example.exchangerateapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.exchangerateapp.ui.ExchangeRateScreen
import com.example.exchangerateapp.workers.SyncExchangeRatesWorker

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExchangeRateScreen()
        }

        // Forzar ejecución manual del Worker
        val workRequest = OneTimeWorkRequestBuilder<SyncExchangeRatesWorker>().build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }
}
