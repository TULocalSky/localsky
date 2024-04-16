package com.ls.localsky.ui.screens

import android.graphics.Bitmap
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import com.ls.localsky.DatabaseLS
import com.ls.localsky.R
import com.ls.localsky.models.UserReport
import com.ls.localsky.models.WeatherItem
import com.ls.localsky.models.WeatherType
import com.ls.localsky.parseTime
import com.ls.localsky.ui.components.CustomMapMarker
import com.ls.localsky.viewmodels.UserReportViewModelLS
import com.ls.localsky.viewmodels.UserViewModelLS
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun MapScreen(
    latitude: Double = 39.9528,
    longitude: Double = -75.1635,
    modifier: Modifier,
    database: DatabaseLS,
    userViewModel: UserViewModelLS,
    userReportViewModel: UserReportViewModelLS
){

    val showBottomSheet = remember { mutableStateOf(false) }
    var currentUserReport by remember { mutableStateOf(Pair<UserReport, Bitmap?>(UserReport(), null))}

    val userPosition = remember{ LatLng(latitude, longitude) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userPosition, 12f)
    }
    var showUserReportScreen by remember {
        mutableStateOf(false)
    }

    val isDarkMode = isSystemInDarkTheme()

    // Load the appropriate map style depending on the current theme
    val mapStyleOptions = if (isDarkMode) {
        // Load dark mode map style
        MapStyleOptions.loadRawResourceStyle(LocalContext.current, R.raw.dark_style)
    } else {
        // Load light mode map style
        MapStyleOptions.loadRawResourceStyle(LocalContext.current, R.raw.light_style)
    }

    Box(modifier = modifier){
        GoogleMap (
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
            ),
            properties = MapProperties(
                mapType = MapType.NORMAL,
                mapStyleOptions = mapStyleOptions
            )
        ) {
            val reports = remember { userReportViewModel.getUserReports() }
            reports.forEach { report ->
                CustomMapMarker(
                    reportAndPic = report.toPair(),
                    onClick = { _ ->
                        currentUserReport = report.toPair()
                        showBottomSheet.value = true
                        false
                    }
                )
            }


        }
        if (showBottomSheet.value) {
            UserReportSheet(
                currentUserReport,
                showBottomSheet,
            )
        }
        if(userViewModel.getCurrentUser().userID != null){
            if(!showUserReportScreen){
                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(20.dp),
                    onClick = { showUserReportScreen = true  },
                    icon = { Icon(Icons.Filled.Edit, "Report") },
                    text = { Text(text = "Report") },
                )

            } else{
                UserReportPopup(
                    submitAction = {
                            picture, condition ->
                        val user = userViewModel.getCurrentUser()
                        database.uploadReport(
                            picture,
                            user,
                            latitude,
                            longitude,
                            condition.weatherSummary,
                            { ref, report ->
                                Log.d("UserReport","Report Uploaded")
                                database.getAllUserReports {
                                    it?.let {
                                        userReportViewModel.setUserReports(it, database)
                                    }
                                }
                            },
                            {
                                Log.d("UserReport","It didnt work")
                            }
                        )
                        showUserReportScreen = false
                    },
                    cancelAction = {
                        showUserReportScreen = false
                    }
                )
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
    submitAction: (Bitmap, WeatherType) -> Unit,
    cancelAction: () -> Unit
){
    val selectedWeatherItem = remember {
        mutableStateOf<WeatherItem?>(null)
    }

    //compose equivalent to using intents which launch photo taking apps
    val userImage = remember { mutableStateOf<Bitmap?>(null) }
    val userImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        userImage.value = it
    }

    val paddingForLazyColumn = 15.dp
    Card (
        modifier = Modifier
            .padding(paddingForLazyColumn),

    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingForLazyColumn),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
            Spacer(modifier = Modifier.padding(5.dp))
            FilledTonalButton(onClick = { userImageLauncher.launch() }) {
                Text("take a pic")
            }
            Spacer(modifier = Modifier.padding(20.dp))
            Text(
                text = "What's the weather looking like?",
            )
            WeatherConditionButtonDisplay(selectedWeatherItem = selectedWeatherItem)
            Spacer(modifier = Modifier.padding(10.dp))
            Spacer(modifier = Modifier.padding(10.dp))
            Row{
                FilledTonalButton(onClick = {
                    if(userImage.value != null){
                        submitAction(userImage.value!!, selectedWeatherItem.value!!.weatherType)
                    }
                }) {
                    Text(text = "Submit")
                }
                Spacer(modifier = Modifier.padding(20.dp))
                FilledTonalButton(onClick = {
                    cancelAction()
                }) {
                    Text(text = "Cancel")
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherConditionButtonDisplay(selectedWeatherItem: MutableState<WeatherItem?>){
    val weatherItems = remember {
        WeatherType.allWeatherTypes.map {
            WeatherItem(it)
        }.toTypedArray()
    }
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
        ) {
            TextField(
                value = selectedWeatherItem.value?.weatherType?.weatherSummary ?: "",
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                weatherItems.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.weatherType.weatherSummary) },
                        onClick = {
                            selectedWeatherItem.value = item
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserReportSheet(
    reportAndPic: Pair<UserReport, Bitmap?>,
    showBottomSheet: MutableState<Boolean>,
){
    val userImage = remember { mutableStateOf(reportAndPic.second) }

    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet.value = false
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if(userImage.value == null){
                Image(
                    painter = painterResource(id = R.drawable.no_photo_jpg ),
                    contentDescription = "local weather image",
                    alignment = Alignment.Center,
                    modifier = Modifier.height(100.dp)
                )
            }else{
                Image(
                    bitmap = userImage.value!!.asImageBitmap(),
                    contentDescription = "local weather image",
                    alignment = Alignment.Center,
                    modifier = Modifier.height(100.dp)
                )
            }
            Spacer(modifier = Modifier.padding(20.dp))
            Text(text = "Today at ${parseTime(reportAndPic.first.createdTime!!)}")
            Spacer(modifier = Modifier.padding(20.dp))
            Divider()
            Spacer(modifier = Modifier.padding(20.dp))
            Text(text = "Weather Condition:")
            Text(text = reportAndPic.first.weatherCondition!!)

        }
    }
}
