package com.ls.localsky.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ls.localsky.models.WeatherData
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

@Composable
fun DailyWeatherDisplay(
    weatherData: WeatherData.Daily.DailyData,
    modifier: Modifier = Modifier,
) {
    val calendar = GregorianCalendar.getInstance()
    val date = Date((weatherData.time*1000).toLong())
    calendar.setTime(date)
    val time = calendar.get(Calendar.DAY_OF_WEEK)

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Column {
            Text(
                text = getDayOfWeek(time)
            )
        }
        Column {

        }
        Column {

        }
        Column {

        }
        Column {
            TemperatureText(
                temperature = weatherData.temperatureHigh,
                fontSize = 12.sp,
            )
        }
        Column {
            TemperatureText(
                temperature = weatherData.temperatureLow,
                fontSize = 12.sp,
            )
        }
    }
}

fun getDayOfWeek(day: Int): String{
    return when(day){
        1 -> {
            "Sunday"
        }
        2 -> {
            "Monday"
        }
        3 -> {
            "Tuesday"
        }
        4 -> {
            "Wednesday"
        }
        5 -> {
            "Thursday"
        }
        6 -> {
            "Friday"
        }
        7 -> {
            "Saturday"
        }
        else -> {
            ""
        }
    }

}