package com.ls.localsky.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.android.gms.maps.model.LatLng
import com.ls.localsky.CacheLS
import com.ls.localsky.ui.components.CurrentWeatherCard
import com.ls.localsky.ui.components.DailyWeatherForecast
import com.ls.localsky.ui.components.HourlyWeatherForecast
import com.ls.localsky.viewmodels.UserViewModelLS
import com.ls.localsky.viewmodels.WeatherViewModelLS

@Composable
fun WeatherScreen(
    viewModelLS: WeatherViewModelLS,
    userViewModel: UserViewModelLS,
    cache: CacheLS,
    modifier: Modifier,
){
    val isRefreshing by viewModelLS.isRefreshing.collectAsStateWithLifecycle()

    val currentLocation = remember {
        userViewModel.getCurrentUserLocation()
    }

    val pullRefreshState = rememberSwipeRefreshState(
            isRefreshing = isRefreshing,
        )
    Surface(
        modifier = modifier,
    ) {
        SwipeRefresh(
            state = pullRefreshState,
            onRefresh = { viewModelLS.getWeatherData(cache) },
            modifier = Modifier.fillMaxSize()
        ) {
            if (currentLocation == null) {
                // Show a loading indicator at the center of the screen
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                )
            } else {
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

    }

}


//@Preview
//@Composable
//fun DefaultWeatherScreen(){
//    WeatherScreen(viewModelLS = WeatherViewModelLS())
//}


