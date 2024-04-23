package com.ls.localsky.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ls.localsky.viewmodels.WeatherViewModelLS

@Composable
fun HourlyWeatherForecast(
    viewModel: WeatherViewModelLS,
){
    viewModel.weatherDataState.weatherData?.hourly?.let { hourly ->
        Card(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(0.dp, 20.dp, 0.dp, 20.dp))
                .padding(16.dp)
//                .blur(
//                    radiusX = 10.dp,
//                    radiusY = 10.dp,
//                    edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(8.dp))
//                )
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(8.dp)
                )

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(brush = Brush.linearGradient(
                        listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)))
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Hourly Forecast",
                    fontSize = 18.sp,
                )
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(content = {
                    items(12) { weatherDataIndex ->
                        HourlyWeatherDisplay(
                            weatherData = hourly.data[weatherDataIndex],
                            modifier = Modifier
                                .height(120.dp)
                                .padding(horizontal = 16.dp)
                                .background(Color.Transparent)
                        )

                    }
                })
            }
        }
    }
}