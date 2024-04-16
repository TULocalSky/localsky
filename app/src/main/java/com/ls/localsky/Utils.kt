package com.ls.localsky

import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val REPORT_VALID_TIME_HOURS = 2

fun parseTime(time: String): String{
    return LocalDateTime.parse(
        time,
        DateTimeFormatter
            .ofPattern("dd/MM/yyyy HH:mm"))
        .format(DateTimeFormatter.ofPattern("HH:mm"))
}

fun parseTimeToHours(time: String) : Int{
    return LocalDateTime.parse(
        time,
        DateTimeFormatter
            .ofPattern("dd/MM/yyyy HH:mm"))
        .format(DateTimeFormatter.ofPattern("HH")).toInt()
}

fun parseTimeToDay(time: String) : Int{
    return LocalDateTime.parse(
        time,
        DateTimeFormatter
            .ofPattern("dd/MM/yyyy HH:mm"))
        .format(DateTimeFormatter.ofPattern("dd")).toInt()
}
fun parseTimeToMonth(time: String) : Int{
    return LocalDateTime.parse(
        time,
        DateTimeFormatter
            .ofPattern("dd/MM/yyyy HH:mm"))
        .format(DateTimeFormatter.ofPattern("dd")).toInt()
}
fun parseTimeToYear(time: String) : Int{
    return LocalDateTime.parse(
        time,
        DateTimeFormatter
            .ofPattern("dd/MM/yyyy HH:mm"))
        .format(DateTimeFormatter.ofPattern("yyyy")).toInt()
}

fun isReportValid(time: String): Boolean{

    val givenTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
    val currentTime = LocalDateTime.now()

    val duration = Duration.between(givenTime, currentTime)

    return duration.toHours() <= REPORT_VALID_TIME_HOURS
}