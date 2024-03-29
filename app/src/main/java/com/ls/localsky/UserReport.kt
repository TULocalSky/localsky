package com.ls.localsky

class UserReport(
    val userReportID: String,
    val user: User,
    val createdTime: String,
    val latitude: Double,
    val longitude: Double,
    val locationPicture: String,
    val weatherCondition: String,
) {

    companion object{
        public val USER_REPORT_TABLE = "UserReports"
    }
}