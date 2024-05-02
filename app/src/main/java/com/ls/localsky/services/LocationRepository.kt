package com.ls.localsky.services

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.ls.localsky.CacheLS
import com.ls.localsky.viewmodels.WeatherViewModelLS
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object LocationRepository {
    val currentLocation = MutableStateFlow<LatLng?>(null)
    lateinit var viewModel: WeatherViewModelLS
    lateinit var cache: CacheLS

    fun updateLocation(location: LatLng) {
        currentLocation.value = location
        if(viewModel != null && currentLocation.value != null){
            viewModel.getWeatherData(cache)
        }
        Log.d("",location.toString())
    }

    // TODO FIX THIS
    fun setRepoViewModel(viewModelLS: WeatherViewModelLS, cacheLS: CacheLS){
        viewModel = viewModelLS
        cache = cacheLS
    }
}