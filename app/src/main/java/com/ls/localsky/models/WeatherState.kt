package com.ls.localsky.models

data class WeatherState(
    val weatherData: WeatherData? = null,
    val isLoading: Boolean = true,
    val isLocationFound: Boolean = false,
    val error: String? = null
)