package com.ls.localsky.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ls.localsky.models.WeatherData
import com.ls.localsky.models.WeatherType
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

@Composable
fun HourlyWeatherDisplay(
    weatherData: WeatherData.Hourly.HourlyData,
    modifier: Modifier = Modifier,
) {
    val calendar = GregorianCalendar.getInstance()
    val date = Date((weatherData.time*1000).toLong())
    calendar.setTime(date)
    val time = calendar.get(Calendar.HOUR)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${time}:00${getAMorPM(calendar.get(Calendar.AM_PM))}"
        )
        Image(
            painter = painterResource(WeatherType.fromWeatherReport(weatherData.summary).iconRes),
            contentDescription = null,
            modifier = Modifier.width(40.dp)
        )
        Text(
            text = weatherData.summary,
            fontWeight = FontWeight.Bold
        )
    }
}

fun getAMorPM(time: Int): String{
    return when(time){
        0 -> {
            "AM"
        }
        1 -> {
            "PM"
        }
        else -> {
            "ERROR"
        }
    }
}