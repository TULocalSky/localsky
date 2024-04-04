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
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ls.localsky.viewmodels.WeatherViewModelLS

@Composable
fun WeatherForecast(
    viewModel: WeatherViewModelLS,
    modifier: Modifier = Modifier
){
    viewModel.weatherDataState.weatherData?.let { data ->
        Card(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
                .padding(16.dp)
                .background(brush = Brush.linearGradient(listOf(Color.LightGray, Color.DarkGray)))
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(Color.LightGray),
                verticalArrangement = Arrangement.SpaceBetween

            ) {
                Text(
                    text = "Today",
                    fontSize = 20.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(content = {
                    items(7) { weatherDataIndex ->
                        DailyWeatherDisplay(
                            weatherData = data.daily.data[weatherDataIndex],
                            modifier = Modifier
                                .height(100.dp)
                                .padding(horizontal = 16.dp)
                                .background(Color.Transparent)
                        )

                    }
                })
            }
        }
    }
}