package com.ls.localsky.services

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object LocationRepository {
    private val _currentLocation = MutableStateFlow(LatLng(0.0, 0.0))
    val currentLocation: StateFlow<LatLng> = _currentLocation.asStateFlow()
    fun updateLocation(location: LatLng?) {
            _currentLocation.value = location!!
    }
}