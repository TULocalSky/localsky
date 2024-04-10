package com.ls.localsky.models

import android.graphics.Bitmap
import com.ls.localsky.models.User

data class UserReport(
    val userReportID: String,
    val user: User,
    val createdTime: String,
    val latitude: Double,
    val longitude: Double,
    val locationPicture: Bitmap,
    val weatherCondition: String,
) {

    companion object{
        val USER_REPORT_TABLE = "UserReports"
    }
}