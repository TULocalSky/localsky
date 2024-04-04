package com.ls.localsky.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ls.localsky.models.WeatherData
import com.ls.localsky.models.WeatherType
import java.text.SimpleDateFormat
import java.util.Calendar

@Composable
fun DailyWeatherDisplay(
    weatherData: WeatherData.Daily.DailyData,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White
) {
    val formattedDate = remember(weatherData) {
            SimpleDateFormat("HH:mm").format(Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = formattedDate,
            color = Color.LightGray
        )
        Image(
            painter = painterResource(WeatherType.fromWeatherReport(weatherData.summary).iconRes),
            contentDescription = null,
            modifier = Modifier.width(40.dp)
        )
        Text(
            text = weatherData.summary,
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}