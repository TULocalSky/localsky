package com.ls.localsky.ui.screens.Map

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.ls.localsky.models.WeatherType

data class WeatherItem(
    val weatherType: WeatherType,
    var isSelected: MutableState<Boolean> = mutableStateOf(false)
)
