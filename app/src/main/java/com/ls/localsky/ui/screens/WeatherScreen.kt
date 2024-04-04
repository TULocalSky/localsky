package com.ls.localsky.ui.screens

import androidx.compose.runtime.Composable
import android.content.Context
import android.graphics.drawable.Icon
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ls.localsky.DatabaseLS
import com.ls.localsky.R
import com.ls.localsky.ui.app.LocalSkyAppRouter
import com.ls.localsky.ui.app.Screen
import com.ls.localsky.ui.components.ButtonComponent
import com.ls.localsky.ui.components.ClickableRegisterText
import com.ls.localsky.ui.components.DividerTextComponent
import com.ls.localsky.ui.components.NormalTextInput
import com.ls.localsky.ui.components.PasswordInput
import com.ls.localsky.ui.components.TitleText
import com.ls.localsky.ui.components.WeatherCard
import com.ls.localsky.ui.components.WeatherDataDisplay
import com.ls.localsky.ui.components.WeatherForecast
import com.ls.localsky.viewmodels.WeatherViewModelLS

@Composable
fun WeatherScreen(
    viewModelLS: WeatherViewModelLS
){
    Surface(
        color = Color.DarkGray,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            WeatherCard(
                modifier = Modifier,
                viewModel = viewModelLS,
                backgroundColor = Color.Blue
            )
            if(viewModelLS.weatherDataState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            WeatherForecast(
                backgroundColor = Color.Green,
                viewModel = viewModelLS
            )
        }
    }

}


@Preview
@Composable
fun DefaultWeatherScreen(){
    WeatherScreen(viewModelLS = WeatherViewModelLS())
}


