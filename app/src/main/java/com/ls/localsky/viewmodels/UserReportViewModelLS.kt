package com.ls.localsky.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.ls.localsky.models.UserReport

class UserReportViewModelLS : ViewModel() {

    private var userReports = mutableStateListOf(UserReport())

    fun getUserReports(): List<UserReport> {
        return userReports.toList()
    }

    fun setUserReports(reports: ArrayList<UserReport>){
        userReports.clear()
        reports.forEach{
            userReports.add(it)
        }
    }

    companion object{
        val TAG = "UserReportViewModel"
    }
}