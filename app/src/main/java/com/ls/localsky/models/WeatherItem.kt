package com.ls.localsky.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.ls.localsky.models.WeatherType

data class WeatherItem(
    val weatherType: WeatherType,
    var isSelected: MutableState<Boolean> = mutableStateOf(false)
)
