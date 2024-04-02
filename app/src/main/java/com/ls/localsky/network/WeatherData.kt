package com.ls.localsky.network

data class WeatherData (
    var latitude: Double,
    var longitude: Double,
    var timezone: String,
    var offset: Double,
    var elevation: Double,
    var currently: Currently,
    var minutely: Minutely,
    var hourly: Hourly,
    var daily: Daily,
    var alerts: Array<Alert>,
    var flags: Flags
) {
    data class Alert(
        var title: String,
        var regions: Array<String>,
        var severity: String,
        var time: Double,
        var expires: Double,
        var description: String,
        var uri: String
    )
    data class Currently(
        var time: Double,
        var summary: String,
        var icon: String,
        var nearestStormDistance: Double,
        var nearestStormBearing: Double,
        var precipIntensity: Double,
        var precipProbability: Double,
        var precipIntensityError: Double,
        var precipType: String,
        var temperature: Double,
        var apparentTemperature: Double,
        var dewPoint: Double,
        var humidity: Double,
        var pressure: Double,
        var windSpeed: Double,
        var windGust: Double,
        var windBearing: Double,
        var cloudCover: Double,
        var uvIndex: Double,
        var visibility: Double,
        var ozone: Double,
    )
    data class Minutely(
        var summary: String,
        var icon: String,
        var data: Array<MinutelyData>
    ){
        data class MinutelyData(
            var time: Double,
            var precipIntensity: Double,
            var precipProbability: Double,
            var precipIntensityError: Double,
            var precipType: String
        )
    }
    data class Hourly(
        var summary: String,
        var icon: String,
        var data: Array<HourlyData>
    ){
        data class HourlyData(
            var time: Double,
            var icon: String,
            var summary: String,
            var precipIntensity: Double,
            var precipProbability: Double,
            var precipIntensityError: Double,
            var precipAccumulation: Double,
            var precipType: String,
            var temperature: Double,
            var apparentTemperature: Double,
            var dewPoint: Double,
            var humidity: Double,
            var pressure: Double,
            var windSpeed: Double,
            var windGust: Double,
            var windBearing: Double,
            var cloudCover: Double,
            var uvIndex: Double,
            var visibility: Double,
            var ozone: Double,
        )
    }
    data class Daily(
        var summary: String,
        var icon: String,
        var data: Array<DailyData>
    ){
        data class DailyData(
            var time: Double,
            var icon: String,
            var summary: String,
            var sunriseTime: Double,
            var sunsetTime: Double,
            var moonPhase: Double,
            var precipIntensity: Double,
            var precipIntensityMax: Double,
            var precipIntensityMaxTime: Double,
            var precipProbability: Double,
            var precipAccumulation: Double,
            var precipType: String,
            var temperatureHigh: Double,
            var temperatureHighTime: Double,
            var temperatureLow: Double,
            var temperatureLowTime: Double,
            var apparentTemperatureHigh: Double,
            var apparentTemperatureHighTime: Double,
            var apparentTemperatureLow: Double,
            var apparentTemperatureLowTime: Double,
            var dewPoint: Double,
            var humidity: Double,
            var pressure: Double,
            var windSpeed: Double,
            var windGust: Double,
            var windGustTime: Double,
            var windBearing: Double,
            var cloudCover: Double,
            var uvIndex: Double,
            var uvIndexTime: Double,
            var visibility: Double,
            var temperatureMin: Double,
            var temperatureMinTime: Double,
            var temperatureMax: Double,
            var temperatureMaxTime: Double,
            var apparentTemperatureMin: Double,
            var apparentTemperatureMinTime: Double,
            var apparentTemperatureMax: Double,
            var apparentTemperatureMaxTime: Double
        )
    }
    data class Flags(
        var sources: Array<String>,
        var sourceTimes: SourceTimes,
        var neareststation: Double,
        var units: String,
        var version: String
    ){
        data class SourceTimes(
            var hrrr_018: String,
            var hrrr_subh: String,
            var hrrr_1848: String,
            var gfs: String,
            var gefs: String
        )
    }

}
