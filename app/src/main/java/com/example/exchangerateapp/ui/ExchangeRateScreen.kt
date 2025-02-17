package com.example.exchangerateapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangeRateScreen(viewModel: ExchangeRateViewModel = viewModel()) {
    val exchangeRates by viewModel.exchangeRates.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Exchange Rates") })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(exchangeRates) { rate ->
                ExchangeRateItem(rate)
            }
        }
    }
}

@Composable
fun ExchangeRateItem(rate: ExchangeRateUI) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "${rate.date}", style = MaterialTheme.typography.labelMedium)
            Text(text = "1 ${rate.baseCurrency} = ${rate.rate} ${rate.targetCurrency}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
