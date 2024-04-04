package com.ls.localsky.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ls.localsky.models.WeatherData

class WeatherViewModelLS: ViewModel(){

    private val fullWeatherData by lazy {
        MutableLiveData<WeatherData>()
    }

    /**
     * Gets the current weather data in the view model
     * @return [LiveData] containing [WeatherData]
     */
    fun getWeatherData(): LiveData<WeatherData>{
        return fullWeatherData
    }

    /**
     * Sets the weather data contained in the view model
     * @param newData [WeatherData] that will be used to update
     */
    fun updateWeatherData(newData: WeatherData){
        fullWeatherData.value = newData
    }


}