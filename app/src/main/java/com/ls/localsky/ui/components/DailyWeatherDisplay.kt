package com.ls.localsky.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ls.localsky.R
import com.ls.localsky.models.WeatherData
import com.ls.localsky.models.WeatherType
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

@Composable
fun DailyWeatherDisplay(
    weatherData: WeatherData.Daily.DailyData,
) {
    val calendar = GregorianCalendar.getInstance()
    val date = Date((weatherData.time*1000).toLong())
    calendar.setTime(date)
    val time = calendar.get(Calendar.DAY_OF_WEEK)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column (
            modifier = Modifier.width(100.dp)
        ){
            Text(
                text = getDayOfWeek(time),
                fontWeight = FontWeight.Bold
            )
        }
        Column {
            Row {
                WeatherDataDisplay(
                    value = (weatherData.precipProbability * 100).toInt(),
                    unit = "%",
                    icon = ImageVector.vectorResource(R.drawable.drop),
                )
                Icon(
                    painter = painterResource(WeatherType.fromWeatherReport(weatherData.icon).iconRes),
                    contentDescription = null,
                    modifier = Modifier.width(40.dp)
                )
                TemperatureText(
                    temperature = weatherData.temperatureLow.toInt(),
                    fontSize = 24.sp,
                )
                TemperatureText(
                    temperature = weatherData.temperatureHigh.toInt(),
                    fontSize = 24.sp,
                )
            }

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