package com.ls.localsky.viewmodels

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.ls.localsky.DatabaseLS
import com.ls.localsky.models.UserReport

class UserReportViewModelLS : ViewModel() {

    private var userReports = mutableStateMapOf(
        Pair<UserReport, Bitmap?>(UserReport(), null))

    fun getUserReports() = userReports

    fun setUserReports(reports: List<UserReport>, database: DatabaseLS){
        userReports.clear()
        reports.forEach{ report ->
            database.getUserReportImage(
                report.locationPicture!!,
                { picture ->
                    userReports.put(report, picture)

                }, {

                })
        }
    }

    companion object{
        val TAG = "UserReportViewModel"
    }
}