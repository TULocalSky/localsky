package com.ls.localsky.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.ls.localsky.ui.components.CustomMapMarker
import com.ls.localsky.ui.components.CustomNavBar

const val MARKER_STATE = "marker state"

@Composable
fun MapScreen(
    latitude: Double = 39.9528,
    longitude: Double = -75.1635,
    modifier: Modifier
){
    val cityHall = remember{ LatLng(latitude, longitude) }
    val cityHallState = rememberMarkerState(MARKER_STATE, cityHall)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cityHall, 12f)
    }

    val mapProperties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }

        Column(
        ) {
            GoogleMap (
                modifier = modifier,
                cameraPositionState = cameraPositionState,
                properties = mapProperties
            ) {
                CustomMapMarker(
                    state = cityHallState,
                    title = "City Hall"
                )
            }
    }
}