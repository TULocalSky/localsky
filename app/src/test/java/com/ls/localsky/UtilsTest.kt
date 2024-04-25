package com.ls.localsky

import com.ls.localsky.models.UserReport
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class UtilsTest {

    @Test
    fun parseTimeTest(){
        val time = "23/04/2024 12:14"
        val parsedTime = parseTime(time)
        val expected = "12:14 PM"
        assertEquals(expected, parsedTime)
    }

    @Test
    fun isLocationValidTest(){
        val userReport = UserReport(
            createdTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
            latitude = 39.98157993,
            longitude = -75.15689202
        )
        val isRight = isReportValidLocation(
            userReport.latitude!!,
            userReport.longitude!!,
            userReport.latitude!! + .001,
            userReport.longitude!! -0.001
        )
        val isWrong = isReportValidLocation(
            userReport.latitude!!,
            userReport.longitude!!,
            userReport.latitude!! + 1,
            userReport.longitude!! -1
        )

        assert(isRight)
        assert(!isWrong)
    }

    fun isTimeValidTest(){

    }
}