package com.ls.localsky

import com.ls.localsky.models.WeatherData
import com.ls.localsky.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherAPI {

    /**
     *
     */
    fun getWeatherData(latitude: Double, longitude: Double, onSuccess:(WeatherData?) -> Unit, onFailure: (Throwable) -> Unit) {
        // 1. Using RetrofitBuilder to build the service
        // 2. Using the ApiService Interface to do a GET Request by providing lat and long
        val call = ApiClient.apiService.getDefaultWeather(latitude.toString(), longitude.toString())

        call.enqueue(object : Callback<WeatherData> {
            // IF success => return back the weather data
            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                if (response.isSuccessful) {
                    val weatherData = response.body()
                    onSuccess(weatherData)
                }
            }
            // IF failure => return back the error
            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                onFailure(t)
            }
        })
    }

companion object {
    val TAG = "WeatherAPI"
}


}




