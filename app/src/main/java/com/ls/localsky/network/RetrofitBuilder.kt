package com.ls.localsky.network

import com.ls.localsky.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // getting the apikey from config file
    private const val APIKEY = BuildConfig.PIRATE_WEATHER_API_KEY
    // set up the base url
    private const val BASE_URL = "https://api.pirateweather.net/forecast/${APIKEY}/"
    // build up the service
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object ApiClient {
    val apiService: ApiService by lazy {
        RetrofitClient.retrofit.create(ApiService::class.java)
    }
}