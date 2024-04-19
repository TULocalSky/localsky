package com.ls.localsky.viewmodels

import androidx.compose.runtime.mutableStateOf

class SensorViewModelLS {
    private var ambientTemperature = mutableStateOf(0f)

    fun setAmbientTemp(temp : Float){ ambientTemperature.value = temp }
    fun getAmbientTempC() = ambientTemperature.value
    fun getAmbientTempF() = ambientTemperature.value*(9/5)+32
}