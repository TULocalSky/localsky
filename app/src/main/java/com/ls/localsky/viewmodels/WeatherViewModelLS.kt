package com.ls.localsky.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ls.localsky.network.WeatherData

class WeatherViewModelLS {

    //Change int to Weather data later
    private val fullWeatherData by lazy {
        MutableLiveData<WeatherData>()
    }

    fun getWeatherData(): LiveData<WeatherData>{
        return fullWeatherData
    }

    fun updateWeatherData(newData: WeatherData){
        fullWeatherData.value = newData
    }


}