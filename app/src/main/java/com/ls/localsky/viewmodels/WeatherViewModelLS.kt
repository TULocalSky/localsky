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

    var fullWeatherData by mutableStateOf(WeatherState())

    /**
     * Gets the current weather data in the view model
     * @return [LiveData] containing [WeatherData]
     */
    fun getWeatherData(): LiveData<WeatherData>{
        WeatherAPI().getWeatherData(40.28517258577531, -75.26480837142107, {
            Log.d("","getting weather data")
            fullWeatherData = fullWeatherData.copy()

        },{
            Log.d(WeatherAPI.TAG, "Error")
        })
    }

    /**
     * Sets the weather data contained in the view model
     * @param newData [WeatherData] that will be used to update
     */
    fun updateWeatherData(newData: WeatherData){
        fullWeatherData.value = newData
    }


}