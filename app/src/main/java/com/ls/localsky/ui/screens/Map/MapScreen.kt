package com.ls.localsky.ui.screens.Map

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.ls.localsky.R
import com.ls.localsky.models.WeatherType
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

    val wi = remember{
        mutableStateListOf(
            WeatherType.allWeatherTypes.map {
                WeatherItem(it)
            }.toTypedArray()
        )
    }

    val weatherItems = remember {
        WeatherType.allWeatherTypes.map {
            WeatherItem(it)
        }.toTypedArray()
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

        if(!showUserReportScreen.value){
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

        if(showUserReportScreen.value){
//            Box(modifier = Modifier
//                .fillMaxSize()
//                .padding(25.dp)
//            ){
//
//            }
            LazyColumn(
                modifier = Modifier
                    .matchParentSize()
                    .padding(25.dp)
                    .background(Color.Gray),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item{
                    Image(
                        painter = painterResource(id = R.drawable.no_photo_jpg ),
                        contentDescription = "",
                        alignment = Alignment.Center
                    )
                }
                item{
                    Spacer(modifier = Modifier.padding(5.dp))
                }
                item{
                    Button(onClick = { /*TODO*/ }) {
                        Text("take a pic")
                    }
                }
                item{
                    Spacer(modifier = Modifier.padding(20.dp))
                }
                item{
                    Text(
                        text = "What's the weather looking like?",
                        color = Color.White
                    )
                }
                item{
                    Spacer(modifier = Modifier.padding(10.dp))
                }
                items(weatherItems){weatherItem ->
                    if(weatherItem.isSelected.value){
                        Button(onClick = { weatherItem.isSelected.value = false }) {
                            Text(
                                text = weatherItem.weatherType.weatherSummary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }else{
                        FilledTonalButton(onClick = { weatherItem.isSelected.value = true }) {
                            Text(text = weatherItem.weatherType.weatherSummary,
                                color = Color.DarkGray,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                item{
                    Spacer(modifier = Modifier.padding(10.dp))
                }
                item{
                    Button(onClick = { showUserReportScreen.value = false }) {
                        Text(text = "submit")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMapScreen (){
    MapScreen(modifier = Modifier.fillMaxSize())
}