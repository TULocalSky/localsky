package com.ls.localsky.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberMarkerState
import com.ls.localsky.models.UserReport

const val MARKER_STATE = "marker state"

@Composable
fun CustomMapMarker(
    latLng: LatLng,
    report: UserReport,
    onClick: (Marker) -> Boolean = { false }
){

    val position = remember{ latLng }
    val markerState = rememberMarkerState(MARKER_STATE, position)
    Marker(
        state = markerState,
        onClick = onClick,
    )
}