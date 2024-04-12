package com.ls.localsky.ui.screens

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.ls.localsky.DatabaseLS
import com.ls.localsky.R
import com.ls.localsky.models.User
import com.ls.localsky.ui.components.CustomMapMarker
import java.time.LocalDateTime

const val MARKER_STATE = "marker state"

@Composable
fun MapScreen(
    latitude: Double = 39.9528,
    longitude: Double = -75.1635,
    database: DatabaseLS,
    modifier: Modifier
){
    val cityHall = remember{ LatLng(latitude, longitude) }
    val picture = getDrawable(LocalContext.current, R.drawable.clearday)!!.toBitmap(100, 100)
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

    Box(modifier = modifier){
        GoogleMap (
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = MapUiSettings(zoomControlsEnabled = false)
        ) {
            CustomMapMarker(
                state = cityHallState,
                title = "City Hall"
            )
        }
        FloatingActionButton(
            onClick = {},
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(Icons.Filled.Add, "Floating action button.")
        }

    }
}

@Composable
fun AddUserReportButton(modifier: Modifier = Modifier, addUserReportButtonSize:TextUnit, showUserReportScreen:MutableState<Boolean>){
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.Black)
            .clickable {
                showUserReportScreen.value = true
                       },
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
//    MapScreen(modifier = Modifier.fillMaxSize())
        }