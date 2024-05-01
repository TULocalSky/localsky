package com.ls.localsky.models

import com.google.android.gms.maps.model.LatLng

class UIState(
    val weatherState: WeatherState? = null,
    val currentUserPosition: LatLng? = null,
)