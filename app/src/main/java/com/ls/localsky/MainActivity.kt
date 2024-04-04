package com.ls.localsky

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.ls.localsky.ui.app.LocalSkyApp
import com.ls.localsky.viewmodels.WeatherViewModelLS

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val database = DatabaseLS()
        val weatherViewModel = ViewModelProvider(this)[WeatherViewModelLS::class.java]
        weatherViewModel.getWeatherData()

        
        setContent {
            LocalSkyApp(database, weatherViewModel)
        }
    }
}

