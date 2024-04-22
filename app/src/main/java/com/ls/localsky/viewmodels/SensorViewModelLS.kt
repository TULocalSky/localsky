package com.ls.localsky.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SensorViewModelLS : ViewModel(){
    private var ambientTemperature = mutableStateOf<Float?>(null)

    fun setAmbientTemp(temp : Float){ ambientTemperature.value = temp }
    fun getAmbientTempC() = ambientTemperature.value
    fun getAmbientTempF() = ambientTemperature.value?.let {
        it * (9/5)+32
    }
}