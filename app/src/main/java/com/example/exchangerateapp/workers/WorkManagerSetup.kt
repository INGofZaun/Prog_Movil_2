package com.example.exchangerateapp.workers

import android.content.Context
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object WorkManagerSetup {

    fun setupSyncWorker(context: Context) {
        val syncWorkRequest = PeriodicWorkRequestBuilder<SyncExchangeRatesWorker>(1, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(context).enqueue(syncWorkRequest)
    }
}
