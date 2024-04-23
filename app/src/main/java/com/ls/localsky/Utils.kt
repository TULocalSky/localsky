package com.ls.localsky

import com.ls.localsky.models.UserReport
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val REPORT_VALID_TIME_HOURS = 2
private val MAX_MARKER_DISTANCE = 0.1

fun parseTime(time: String): String{
    return LocalDateTime.parse(
        time,
        DateTimeFormatter
            .ofPattern("dd/MM/yyyy HH:mm"))
        .format(DateTimeFormatter.ofPattern("HH:mm"))
}

fun isReportValid(report: UserReport, userLat: Double, userLong: Double) : Boolean{
    return isReportValidTime(report.createdTime!!) &&
            isReportValidLocation(userLat, userLong, report.latitude!!, report.longitude!!)
}

fun isReportValidLocation(userLat: Double, userLong: Double, givenLat: Double, givenLong: Double): Boolean {
    val latDifference = Math.abs(userLat - givenLat)
    val longDifference = Math.abs(userLong - givenLong)

    return latDifference <= MAX_MARKER_DISTANCE && longDifference <= MAX_MARKER_DISTANCE
}

fun isReportValidTime(time: String): Boolean{
    val givenTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
    val currentTime = LocalDateTime.now()

    val duration = Duration.between(givenTime, currentTime)

    return duration.toHours() <= REPORT_VALID_TIME_HOURS
}