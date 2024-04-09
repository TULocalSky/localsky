package com.ls.localsky.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ls.localsky.ui.components.CurrentWeatherCard
import com.ls.localsky.ui.components.DailyWeatherForecast
import com.ls.localsky.ui.components.HourlyWeatherForecast
import com.ls.localsky.viewmodels.WeatherViewModelLS

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeatherScreen(
    viewModelLS: WeatherViewModelLS
){
    val isRefreshing by viewModelLS.isRefreshing.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            viewModelLS.getWeatherData()
        })
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn {
            item{
                CurrentWeatherCard(
                    viewModel = viewModelLS
                )
            }
            item{
                if(viewModelLS.weatherDataState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.CenterHorizontally),
                    )
                }
            }
            item{
                HourlyWeatherForecast(
                    viewModel = viewModelLS
                )
            }
            item{
                DailyWeatherForecast(
                    viewModel = viewModelLS
                )
            }
            item{
                Spacer(
                    Modifier.height(200.dp)
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


