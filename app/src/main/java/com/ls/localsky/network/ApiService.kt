package com.ls.localsky.network

import com.ls.localsky.models.WeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("{latitude},{longitude}")
    fun getDefaultWeather(
        @Path("latitude") lat: String,
        @Path("longitude") lon: String
    ): Call<WeatherData>

}