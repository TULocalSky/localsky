package com.ls.localsky.ui.screens

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.ls.localsky.DatabaseLS
import com.ls.localsky.R
import com.ls.localsky.models.UserReport
import com.ls.localsky.services.LocationRepository
import com.ls.localsky.ui.components.CustomMapMarker
import com.ls.localsky.ui.components.MARKER_STATE
import com.ls.localsky.ui.components.UserReportSheet
import com.ls.localsky.ui.components.showUserReportScreen
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
    currentLocation: LatLng?
){
    val showBottomSheet = remember { mutableStateOf(false) }
    var currentUserReport by remember { mutableStateOf(Pair<UserReport, Bitmap?>(UserReport(), null))}

    val userPosition = remember {
        currentLocation?.latitude?.let {
            LatLng(it, currentLocation.longitude)
        }
    }

    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(userPosition) {
        // Check if userPosition is not null
        userPosition?.let { position ->
            // Animate the camera to the user's position
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(position, 12f),
                durationMs = 1000
            )
        }
    }

    val currentLocationMarkerState = userPosition?.let { rememberMarkerState(MARKER_STATE, it) }

    var showUserReportScreen = remember {
        mutableStateOf(false)
    }

    val isDarkMode = isSystemInDarkTheme()

    // Load the appropriate map style depending on the current theme
    val mapStyleOptions = getMapStyleOptions(isDarkMode, LocalContext.current)

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
            if (currentLocationMarkerState != null) {
                Marker(
                    state = currentLocationMarkerState
                )
            }
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
            showUserReportScreen(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp),
                sensorViewModel = sensorViewModel,
                showUserReportScreen = showUserReportScreen,
                userViewModel = userViewModel,
                userReportViewModel = userReportViewModel,
                database = database,
                currentLocation =currentLocation
            )
        }
    }
}

fun getMapStyleOptions(
    isDarkMode: Boolean,
    context: Context
): MapStyleOptions{
    return if (isDarkMode) {
        // Load dark mode map style
        MapStyleOptions.loadRawResourceStyle(context, R.raw.dark_style)
    } else {
        // Load light mode map style
        MapStyleOptions.loadRawResourceStyle(context, R.raw.light_style)
    }
}