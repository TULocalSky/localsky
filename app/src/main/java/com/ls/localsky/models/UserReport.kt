package com.ls.localsky.models

import android.graphics.Bitmap
import androidx.compose.ui.util.packInts
import com.ls.localsky.models.User

data class UserReport(
    val userID: String? = null,
    val createdTime: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val locationPicture: String? = null,
    val weatherCondition: String? = null,
    val reportedTemperature: String? = null,
    val reportedRelativeHumidity: String? = null,
) {

    @Override
    override fun toString(): String = userID + "\n" + createdTime + "\n" + latitude + "\n" + longitude + "\n" + locationPicture + "\n" + weatherCondition

    companion object{
        val USER_REPORT_TABLE = "UserReports"
        val USER_ID = "userID"
        val CREATED_TIME = "createdTime"
        val LATITUDE = "latitude"
        val LONGITUDE = "longitude"
        val LOCATION_PICTURE = "locationPicture"
        val WEATHER_CONDITION = "weatherCondition"
        val REPORTED_TEMPERATURE = "reportedTemperature"
        val REPORTED_RELATIVE_HUMIDITY = "reportedRelativeHumidity"
    }
}