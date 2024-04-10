package com.ls.localsky.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.ls.localsky.ui.components.CustomMapMarker

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
    val showUserReportScreen: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }

    val mapProperties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }

    val addUserReportButtonSize = 50

    Box(Modifier.fillMaxSize()){
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

        AddUserReportButton(
            modifier = Modifier
                .size(addUserReportButtonSize.dp)
                .padding(3.dp)
                .align(Alignment.BottomStart),
            addUserReportButtonSize.sp,
            showUserReportScreen
        )
    }
}

@Composable
fun AddUserReportButton(modifier: Modifier = Modifier, addUserReportButtonSize:TextUnit, showUserReportScreen:MutableState<Boolean>){
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.Black)
            .clickable { showUserReportScreen.value = true },
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "+",
            color = Color.White,
            fontSize =  addUserReportButtonSize/2,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMapScreen (){
    MapScreen(modifier = Modifier.fillMaxSize())}