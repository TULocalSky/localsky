package com.ls.localsky.viewmodels

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.ls.localsky.CacheLS
import com.ls.localsky.WeatherAPI
import com.ls.localsky.models.WeatherData
import com.ls.localsky.models.WeatherState
import com.ls.localsky.services.LocationRepository
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
        cache: CacheLS,
    ){
        _isRefreshing.value = true

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
                _isRefreshing.value = false
            }
        }
        LocationRepository.currentLocation.value?.let {
            WeatherAPI().getWeatherData(it.latitude, it.longitude, {
                Log.d(TAG,"Getting New Weather Data for the View Model")
                weatherDataState = weatherDataState.copy(
                    weatherData = it,
                    isLoading = false,
                    isLocationFound = true,
                    error = null
                )
                cache.updateCachedWeatherData(it!!)
                _isRefreshing.value = false

            },{error ->
                Log.d(WeatherAPI.TAG, "Error $error")
                weatherDataState = weatherDataState.copy(
                    isLoading = false,
                    isLocationFound = true,
                    error = error.toString()
                )
                // Probably should wait some amount of time to restart
                getWeatherData(cache)
                _isRefreshing.value = false


            })
        }
    }

    fun setCoordinate(location: LatLng) {
        weatherDataState = weatherDataState.copy(
            isLoading = false,
            isLocationFound = true,
            error = null
        )
    }

    companion object{
        val TAG = "Weather ViewModel"
    }

}