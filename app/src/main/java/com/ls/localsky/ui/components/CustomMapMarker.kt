package com.ls.localsky.ui.components

import androidx.compose.runtime.Composable
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun CustomMapMarker(
    state: MarkerState,
    title: String,
    onClick: (Marker) -> Boolean = { false }
){
    Marker(
        state = state,
        title = title,
        onClick = onClick,
    )
}