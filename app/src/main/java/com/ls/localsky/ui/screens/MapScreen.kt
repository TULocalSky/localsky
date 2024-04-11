package com.ls.localsky.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.ls.localsky.ui.components.CustomMapMarker

const val MARKER_STATE = "marker state"

@Composable
fun MapScreen(
    latitude: Double = 39.9528,
    longitude: Double = -75.1635,
    modifier: Modifier,
    innerPadding: PaddingValues = PaddingValues()
){
    val cityHall = remember{ LatLng(latitude, longitude) }
    val cityHallState = rememberMarkerState(MARKER_STATE, cityHall)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cityHall, 12f)
    }
    val showUserReportScreen: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }

    Box(Modifier.fillMaxSize()){
        GoogleMap (
            modifier = modifier,
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false
            )
        ) {
            CustomMapMarker(
                state = cityHallState,
                title = "City Hall"
            )
        }

        Button(
            onClick = { showUserReportScreen.value = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    horizontal = 10.dp,
                    vertical = innerPadding.calculateBottomPadding() + 15.dp
                ),
            border = BorderStroke(1.dp, Color.Black)
            ) {
            Text("Report")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMapScreen (){
    MapScreen(modifier = Modifier.fillMaxSize())}