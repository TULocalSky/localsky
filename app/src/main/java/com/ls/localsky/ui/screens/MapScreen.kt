package com.ls.localsky.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(
    latitude: Double = 39.9528,
    longitude: Double = 75.1635
){
    val cityHall = LatLng(latitude, longitude)
    val cityHallState = MarkerState(position = cityHall)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cityHall, 10f)
    }

    GoogleMap (
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = cityHallState,
            title = "City Hall"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMapScreen(){
    MapScreen()
}