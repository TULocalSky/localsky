package com.ls.localsky.ui.screens

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.ls.localsky.services.LocationRepository
import com.ls.localsky.ui.components.CustomMapMarker
import com.ls.localsky.ui.components.UserReportPopup
import com.ls.localsky.ui.components.UserReportSheet
import com.ls.localsky.viewmodels.SensorViewModelLS
import com.ls.localsky.viewmodels.UserReportViewModelLS
import com.ls.localsky.viewmodels.UserViewModelLS


@Composable
fun MapScreen(
    modifier: Modifier,
    database: DatabaseLS,
    userViewModel: UserViewModelLS,
    userReportViewModel: UserReportViewModelLS,
    sensorViewModel: SensorViewModelLS,
){
    val currentLocation by LocationRepository.currentLocation.collectAsState()

    val showBottomSheet = remember { mutableStateOf(false) }
    var currentUserReport by remember { mutableStateOf(Pair<UserReport, Bitmap?>(UserReport(), null))}

    val userPosition = remember{
        currentLocation?.latitude?.let {
            currentLocation?.longitude?.let { it1 ->
                LatLng(it, it1)
            }
        }
    }
    val cameraPositionState = rememberCameraPositionState {
        position = userPosition?.let { CameraPosition.fromLatLngZoom(it, 12f) }!!
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
                    sensorViewModel = sensorViewModel,
                    submitAction = {
                            picture, condition ->
                        val user = userViewModel.getCurrentUser()
                        currentLocation?.latitude?.let {
                            database.uploadReport(
                                picture,
                                user,
                                it,
                                currentLocation!!.longitude,
                                condition.weatherSummary,
                                null,
                                { ref, report ->
                                    Log.d("UserReport","Report Uploaded")
                                    userViewModel.getCurrentUserLocation()?.let { latlng ->
                                        database.getAllUserReports (latlng){
                                            it?.let {
                                                userReportViewModel.setUserReports(it, database)
                                            }
                                        }
                                    }
                                },
                                {
                                    Log.d("UserReport","It didnt work")
                                }
                            )
                        }
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