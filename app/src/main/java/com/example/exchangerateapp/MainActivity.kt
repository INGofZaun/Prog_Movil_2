package com.example.exchangerateapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.exchangerateapp.ui.ExchangeRateScreen
import com.example.exchangerateapp.ui.theme.ExchangeRateAppTheme // IMPORTACIÓN CORRECTA

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExchangeRateAppTheme {
                ExchangeRateScreen()
            }
        }
    }
}
