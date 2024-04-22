package com.ls.localsky.ui.components

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState
import com.ls.localsky.R
import com.ls.localsky.models.UserReport

const val MARKER_STATE = "marker state"

@Composable
fun CustomMapMarker(
    reportAndPic: Pair<UserReport?, Bitmap?>,
    onClick: (Marker) -> Boolean = { false },
){

    val latitude = reportAndPic.first?.latitude
    val longitude = reportAndPic.first?.longitude

    if(latitude != null && longitude != null){
        val position = remember { LatLng(latitude, longitude) }
        val markerState = rememberMarkerState(MARKER_STATE,position)
        val markerImage = remember(reportAndPic.second) {
            if(reportAndPic.second == null){
                BitmapDescriptorFactory.fromResource(R.drawable.no_photo_jpg )
            } else{
                val customizedBitmap = createCircularBitmap(reportAndPic.second!!)
                BitmapDescriptorFactory.fromBitmap(customizedBitmap)
            }
        }

        Marker(
            state = markerState,
            onClick = onClick,
            icon = markerImage
        )
    }

}

// Function to convert a bitmap to a circular shape and add a border around it
fun createCircularBitmap(bitmap: Bitmap, size: Int = 128, borderSize: Int = 4): Bitmap {
    // Resize the bitmap
    val resizedBitmap = Bitmap.createScaledBitmap(bitmap, size, size, false)

    // Create a new bitmap with added border
    val circularBitmap = Bitmap.createBitmap(size + borderSize * 2, size + borderSize * 2, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(circularBitmap)

    // Draw the circular image with a border
    val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    // Draw a circular mask
    val path = Path()
    path.addCircle((size + borderSize).toFloat() / 2, (size + borderSize).toFloat() / 2, (size + borderSize).toFloat() / 2 - borderSize, Path.Direction.CW)
    canvas.clipPath(path)

    // Draw the resized bitmap in the center of the border
    canvas.drawBitmap(resizedBitmap, borderSize.toFloat(), borderSize.toFloat(), paint)

    // Draw the border around the circular image
    paint.color = Color.BLACK
    paint.style = Paint.Style.STROKE
    paint.strokeWidth = borderSize.toFloat()
    canvas.drawCircle((size + borderSize).toFloat() / 2, (size + borderSize).toFloat() / 2, (size + borderSize).toFloat() / 2 - borderSize / 2, paint)

    // Return the circular bitmap with border
    return circularBitmap
}