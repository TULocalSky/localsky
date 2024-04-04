package com.ls.localsky.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ls.localsky.WeatherAPI
import com.ls.localsky.models.WeatherData
import com.ls.localsky.models.WeatherState

class WeatherViewModelLS: ViewModel(){

    var weatherDataState by mutableStateOf(WeatherState())

    /**
     * Gets the current weather data in the view model
     * @return [LiveData] containing [WeatherData]
     */
    fun getWeatherData(){
        // Have the weather data be loading until it gets its info
        weatherDataState = weatherDataState.copy(
            isLoading = true,
            error = null
        )
        WeatherAPI().getWeatherData(40.28517258577531, -75.26480837142107, {
            Log.d("","getting weather data")
            weatherDataState = weatherDataState.copy(
                weatherData = it,
                isLoading = false,
                error = null
            )

        },{
            Log.d(WeatherAPI.TAG, "Error")
            weatherDataState = weatherDataState.copy(
                weatherData = null,
                isLoading = false,
                error = it.toString()
            )
        })
    }

}