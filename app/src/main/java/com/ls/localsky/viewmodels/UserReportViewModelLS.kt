package com.ls.localsky.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.ls.localsky.models.UserReport

class UserReportViewModelLS : ViewModel() {

    private var userReports = mutableStateListOf(UserReport())

    fun getUserReports(): SnapshotStateList<UserReport> {
        return userReports
    }

    fun setUserReports(reports: List<UserReport>){
        userReports.clear()
        reports.forEach{
            userReports.add(it)
        }
    }

    companion object{
        val TAG = "UserReportViewModel"
    }
}