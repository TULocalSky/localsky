package com.ls.localsky.models

public final data class WeatherState(
    val weatherData: WeatherData? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)