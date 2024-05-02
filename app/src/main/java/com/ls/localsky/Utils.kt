package com.ls.localsky

import com.ls.localsky.models.UserReport
import com.ls.localsky.models.WeatherItem
import com.ls.localsky.models.WeatherType
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

private val REPORT_VALID_TIME_HOURS = 2
// TODO Change this back to 0.01
private val MAX_MARKER_DISTANCE = 0.01

fun parseTime(time: String): String{
    return LocalDateTime.parse(
        time,
        DateTimeFormatter
            .ofPattern("dd/MM/yyyy HH:mm a"))
        .format(DateTimeFormatter.ofPattern("hh:mm a"))
}

fun isReportValid(report: UserReport, userLat: Double, userLong: Double) : Boolean{
    return isReportValidTime(report.createdTime!!) &&
            isReportValidLocation(userLat, userLong, report.latitude!!, report.longitude!!)
}

fun isReportValidLocation(userLat: Double, userLong: Double, givenLat: Double, givenLong: Double): Boolean {
    val latDifference = Math.abs(userLat - givenLat)
    val longDifference = Math.abs(userLong - givenLong)

    return latDifference <= MAX_MARKER_DISTANCE && longDifference <= MAX_MARKER_DISTANCE
}

fun isReportValidTime(time: String): Boolean{
    val givenTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm a"))
    val currentTime = LocalDateTime.now()

    val duration = Duration.between(givenTime, currentTime)

    return duration.toHours() <= REPORT_VALID_TIME_HOURS
}

fun isDay(nowTime: LocalTime): Boolean{
    val startDayTime = LocalTime.parse("05:00:00")
    val endDayTime = LocalTime.parse("20:00:00")
    return !nowTime.isBefore(startDayTime) && !nowTime.isAfter(endDayTime)
}

fun convertWeatherSummary(weatherSummary: String?): String{
    return if (weatherSummary == null){
        " "
    } else {
        when (weatherSummary) {
            "clear-" ->
                "Clear"
            "clear-day" ->
                "Clear Day"
            "clear-night" ->
                "Clear Night"
            "fog" ->
                "Foggy"
            "sleet" ->
                "Sleet"
            "snow" ->
                "Snowy"
            "rain" ->
                "Rainy"
            "wind" ->
                "Windy"
            "partly-cloudy-" ->
                "Partly Cloudy"
            "partly-cloudy-day" ->
                "Partly Cloudy Day"
            "partly-cloudy-night" ->
                "Partly Cloudy Night"
            "cloudy" ->
                "Cloudy"
            else ->
                "Error"
        }
    }
}

fun appendTimeofDay(weatherItem: WeatherItem) : WeatherItem {
    val nowTime = LocalTime.now()

    val weatherSummary = weatherItem.weatherType.weatherSummary
    if(weatherSummary.equals("clear-")){
        return if(isDay(nowTime)){
            weatherItem.copy(
                weatherType = WeatherType.ClearDay
            )
        } else {
            weatherItem.copy(
                weatherType = WeatherType.ClearNight
            )
        }
    } else if(weatherSummary.equals("partly-cloudy-")){
        return if(isDay(nowTime)){
            weatherItem.copy(
                weatherType = WeatherType.PartlyCloudyDay
            )
        } else {
            weatherItem.copy(
                weatherType = WeatherType.PartlyCloudyNight
            )
        }
    }
    return weatherItem
}