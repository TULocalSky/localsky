package com.ls.localsky.ui.components

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberMarkerState
import com.ls.localsky.DatabaseLS
import com.ls.localsky.R
import com.ls.localsky.models.UserReport

const val MARKER_STATE = "marker state"

@Composable
fun CustomMapMarker(
    report: UserReport,
    onClick: (Marker) -> Boolean = { false },
    database: DatabaseLS
){

    val position = remember { LatLng(report.latitude!!, report.longitude!!) }
    val markerState = rememberMarkerState(MARKER_STATE,position)
    val markerImage = remember { mutableStateOf<BitmapDescriptor?>(null) }

    val onImageSuccess: (Bitmap) -> Unit = { bitmap ->
        val customizedBitmap = createCircularBitmap(bitmap)
        val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(customizedBitmap)
        markerImage.value = bitmapDescriptor
    }

    val onImageFailure: () -> Unit = {
        val bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.no_photo_jpg )
        markerImage.value = bitmapDescriptor
    }

    database.getUserReportImage(report.locationPicture!!, onImageSuccess, onImageFailure)

    Marker(
        state = markerState,
        onClick = onClick,
        icon = markerImage.value
    )
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
    paint.color = Color.BLACK // Set the border color (black)
    paint.style = Paint.Style.STROKE
    paint.strokeWidth = borderSize.toFloat()
    canvas.drawCircle((size + borderSize).toFloat() / 2, (size + borderSize).toFloat() / 2, (size + borderSize).toFloat() / 2 - borderSize / 2, paint)

    // Return the circular bitmap with border
    return circularBitmap
}