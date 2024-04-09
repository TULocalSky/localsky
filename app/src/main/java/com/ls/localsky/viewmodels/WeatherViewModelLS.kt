package com.ls.localsky.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ls.localsky.CacheLS
import com.ls.localsky.WeatherAPI
import com.ls.localsky.models.WeatherData
import com.ls.localsky.models.WeatherState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModelLS: ViewModel(){

    var weatherDataState by mutableStateOf(WeatherState())


    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    /**
     * Gets the current weather data in the view model
     * @return [LiveData] containing [WeatherData]
     */
    fun getWeatherData(
        cache: CacheLS
    ){

        // Have the weather data be loading until it gets its info
        weatherDataState = weatherDataState.copy(
            isLoading = true,
            error = null
        )

        CoroutineScope(Dispatchers.IO).launch {
            cache.getCachedWeatherData()?.let {
                weatherDataState = weatherDataState.copy(
                    weatherData = it,
                    isLoading = false,
                    error = null
                )
            }
        }
        WeatherAPI().getWeatherData(40.28517258577531, -75.26480837142107, {
            Log.d(TAG,"Getting New Weather Data for the View Model")
            weatherDataState = weatherDataState.copy(
                weatherData = it,
                isLoading = false,
                error = null
            )
            cache.updateCachedWeatherData(it!!)

        },{
            Log.d(WeatherAPI.TAG, "Error $it")
            weatherDataState = weatherDataState.copy(
                isLoading = false,
                error = it.toString()
            )
            // Probably should wait some amount of time to restart
            getWeatherData(cache)

        })
    }

    companion object{
        val TAG = "Weather ViewModel"
    }

}