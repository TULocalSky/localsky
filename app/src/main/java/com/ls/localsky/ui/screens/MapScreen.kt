package com.ls.localsky.ui.screens

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
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
import com.ls.localsky.DatabaseLS
import com.ls.localsky.R
import com.ls.localsky.models.User
import com.ls.localsky.models.WeatherItem
import com.ls.localsky.models.WeatherType
import com.ls.localsky.ui.components.CustomMapMarker

const val MARKER_STATE = "marker state"

@Composable
fun MapScreen(
    latitude: Double = 39.9528,
    longitude: Double = -75.1635,
    modifier: Modifier,
    database: DatabaseLS
){
    val cityHall = remember{ LatLng(latitude, longitude) }
    val cityHallState = rememberMarkerState(MARKER_STATE, cityHall)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cityHall, 12f)
    }
    var showUserReportScreen by remember {
        mutableStateOf(false)
    }

    Box(modifier = modifier){
        GoogleMap (
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

        if(!showUserReportScreen){
            Button(
                onClick = { showUserReportScreen = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        horizontal = 10.dp,
                    ),
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Text("Report")
            }

        }

        if(showUserReportScreen){
            UserReportPopup{picture, condition ->
                val user = User(
                    database.getCurrentUser()!!.uid,
                    "",
                    "",
                    "",
                )
                database.uploadReport(
                    picture,
                    user,
                    latitude,
                    longitude,
                    condition.weatherSummary,
                    { ref, report ->

                    },
                    {}
                )
                showUserReportScreen = false
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMapScreen (){
}


@Composable
fun UserReportPopup(
    submitAction: (Bitmap, WeatherType) -> Unit
){

    val weatherItems = remember {
        WeatherType.allWeatherTypes.map {
            WeatherItem(it)
        }.toTypedArray()
    }
    var selectedWeatherItem by remember {
        mutableStateOf<WeatherItem?>(null)
    }

    //compose equivalent to using intents which launch photo taking apps
    val userImage = remember { mutableStateOf<Bitmap?>(null) }
    val userImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        userImage.value = it
    }

    val paddingForLazyColumn = 15.dp

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                start = paddingForLazyColumn,
                top = paddingForLazyColumn,
                end = paddingForLazyColumn,
                bottom = paddingForLazyColumn
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item{
            if(userImage.value == null){
                Image(
                    painter = painterResource(id = R.drawable.no_photo_jpg ),
                    contentDescription = "local weather image",
                    alignment = Alignment.Center
                )
            }else{
                Image(
                    bitmap = userImage.value!!.asImageBitmap(),
                    contentDescription = "local weather image",
                    alignment = Alignment.Center
                )
            }
        }
        item{
            Spacer(modifier = Modifier.padding(5.dp))
        }
        item{
            Button(onClick = { userImageLauncher.launch() }) {
                Text("take a pic")
            }
        }
        item{
            Spacer(modifier = Modifier.padding(20.dp))
        }
        item{
            Text(
                text = "What's the weather looking like?",
            )
        }
        item{
            Spacer(modifier = Modifier.padding(10.dp))
        }
        items(weatherItems){weatherItem ->
            if(weatherItem.isSelected.value){
                Button(onClick = {
                    weatherItem.isSelected.value = false
                }) {
                    Text(
                        text = weatherItem.weatherType.weatherSummary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }else{
                ElevatedButton(onClick = {
                    if(selectedWeatherItem == null){
                        selectedWeatherItem = weatherItem
                        weatherItem.isSelected.value = true
                    }else{
                        selectedWeatherItem!!.isSelected.value = false
                        selectedWeatherItem = weatherItem
                        weatherItem.isSelected.value = true
                    }
                }) {
                    Text(text = weatherItem.weatherType.weatherSummary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        item{
            Spacer(modifier = Modifier.padding(10.dp))
        }
        item{
            Button(onClick = {
                if((userImage.value != null) && (selectedWeatherItem != null)){
                    submitAction(userImage.value!!, selectedWeatherItem!!.weatherType)
                }
            }) {
                Text(text = "submit")
            }
        }
    }
}