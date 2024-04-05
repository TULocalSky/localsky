package com.ls.localsky.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ls.localsky.ui.components.WeatherCard
import com.ls.localsky.ui.components.WeatherForecast
import com.ls.localsky.viewmodels.WeatherViewModelLS

@Composable
fun WeatherScreen(
    viewModelLS: WeatherViewModelLS
){
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn {
            item{
                WeatherCard(
                    viewModel = viewModelLS
                )
            }
            item{
                if(viewModelLS.weatherDataState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.fillMaxWidth()
                            .wrapContentWidth(align = Alignment.CenterHorizontally),
                    )
                }
            }
            item{
                WeatherForecast(
                    viewModel = viewModelLS
                )
            }
        }
    }

}


@Preview
@Composable
fun DefaultWeatherScreen(){
    WeatherScreen(viewModelLS = WeatherViewModelLS())
}


