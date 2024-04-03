package com.ls.localsky.models

import androidx.annotation.DrawableRes
import com.ls.localsky.R

sealed class WeatherType(
    val weatherSummary: String,
    @DrawableRes val iconRes: Int
) {
    object ClearDay : WeatherType(
        weatherSummary = "clear-day",
        iconRes = R.drawable.clearday
    )

    object ClearNight : WeatherType(
        weatherSummary = "clear-night",
        iconRes = R.drawable.clearnight
    )

    object Cloudy : WeatherType(
        weatherSummary = "cloudy",
        iconRes = R.drawable.cloudy
    )

    object Foggy : WeatherType(
        weatherSummary = "fog",
        iconRes = R.drawable.fog
    )

    object PartlyCloudyDay : WeatherType(
        weatherSummary = "partly-cloudy-day",
        iconRes = R.drawable.partlycloudyday
    )

    object PartlyCloudyNight : WeatherType(
        weatherSummary = "partly-cloudy-night",
        iconRes = R.drawable.partlycloudynight
    )

    object Rain : WeatherType(
        weatherSummary = "rain",
        iconRes = R.drawable.rain
    )

    object Sleet : WeatherType(
        weatherSummary = "sleet",
        iconRes = R.drawable.sleet__2_
    )

    object Snow : WeatherType(
        weatherSummary = "snow",
        iconRes = R.drawable.snow
    )

    object Windy : WeatherType(
        weatherSummary = "wind",
        iconRes = R.drawable.wind__2_
    )

    companion object {
        fun fromWeatherReport(summary: String): WeatherType {
            return when (summary) {
                "clear-day" -> ClearDay
                "fog" -> Foggy
                "sleet" -> Sleet
                "snow" -> Snow
                "rain" -> Rain
                "wind" -> Windy
                "partly-cloudy-night" -> PartlyCloudyNight
                "partly-cloudy-day" -> PartlyCloudyDay
                "cloudy" -> Cloudy
                "clear-night" -> ClearNight
                else -> ClearDay
            }
        }
    }
}
