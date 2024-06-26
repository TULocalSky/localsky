package com.ls.localsky.viewmodels

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.ls.localsky.DatabaseLS
import com.ls.localsky.models.UserReport
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class UserReportViewModelLS : ViewModel() {

    private var userReports = mutableStateMapOf(
        Pair<UserReport, Bitmap?>(UserReport(), null))

    fun getUserReports() = userReports

    fun refreshUserReports(report: UserReport, database: DatabaseLS){
        userReports.put(report, null)
        database.getUserReportImage(
            report.locationPicture!!,
            { picture ->
                Log.d("Picture", "Got the picture $picture")
                val correctReport = report.copy(
                    createdTime = LocalDateTime.parse(report.createdTime, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")).format(
                        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm a"))
                )
                userReports.put(correctReport, picture)
            }, {
                Log.d("Storage","Failed to get picture")
            })
    }

    fun setUserReports(reports: List<UserReport>, database: DatabaseLS){
        userReports.clear()
        reports.forEach{ report ->
            userReports.put(report, null)
            database.getUserReportImage(
                report.locationPicture!!,
                { picture ->
                    Log.d("Picture", "Got the picture $picture")
                    userReports.put(report, picture)
                }, {
                    Log.d("Storage","Failed to get picture")
                })
        }
    }

    companion object{
        val TAG = "UserReportViewModel"
    }
}