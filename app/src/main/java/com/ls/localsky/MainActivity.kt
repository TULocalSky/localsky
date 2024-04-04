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
        val weatherAPI = WeatherAPI()
        weatherAPI.getWeatherData(40.28517258577531, -75.26480837142107, {
            Log.d("","getting weather data")
            weatherViewModel.updateWeatherData(it!!)

        },{
            Log.d(WeatherAPI.TAG, "Error")
        })

        
        setContent {
            LocalSkyApp(database, weatherViewModel)
        }
    }
}

