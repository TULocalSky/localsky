package com.ls.localsky.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SensorViewModelLS : ViewModel(){
    private var ambientTemperature = mutableStateOf<Float?>(null)

    fun setAmbientTemp(temp : Float){ ambientTemperature.value = temp }
    fun getAmbientTempC() = ambientTemperature.value?.let {
        "$it°C"
    }
    fun getAmbientTempF() = ambientTemperature.value?.let {
        val temp = it * (9/5)+32
        "$temp°F"
    }
}