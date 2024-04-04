package com.ls.localsky.ui.app

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.ls.localsky.DatabaseLS
import com.ls.localsky.ui.screens.LoginScreen
import com.ls.localsky.ui.screens.MapScreen
import com.ls.localsky.ui.screens.RegisterScreen
import com.ls.localsky.ui.screens.SettingsScreen
import com.ls.localsky.ui.screens.WeatherScreen
import com.ls.localsky.viewmodels.WeatherViewModelLS

@Composable
fun LocalSkyApp(
    database: DatabaseLS,
    weatherViewModelLS: WeatherViewModelLS,
){

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Log.d("Logged in user", database.getCurrentUser().toString())
        if(database.getCurrentUser() != null){
            LocalSkyAppRouter.navigateTo(Screen.WeatherScreen)
        }
        Crossfade(targetState = LocalSkyAppRouter.currentScreen, label = "") { currentState ->
            when(currentState.value) {
                is Screen.LoginScreen -> {
                    LoginScreen(LocalContext.current, database)
                }
                is Screen.RegisterScreen -> {
                    RegisterScreen(LocalContext.current, database)
                }
                is Screen.MapScreen -> {
                    MapScreen()
                }
                is Screen.SettingsScreen -> {
                    SettingsScreen()
                }
                is Screen.WeatherScreen -> {
                    WeatherScreen(weatherViewModelLS)
                }
            }
        }

    }

}