package com.ls.localsky.ui.app

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.ls.localsky.DatabaseLS
import com.ls.localsky.ui.components.CustomNavBar
import com.ls.localsky.ui.theme.LocalSkyTheme
import com.ls.localsky.viewmodels.WeatherViewModelLS

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LocalSkyApp(
    database: DatabaseLS,
    weatherViewModelLS: WeatherViewModelLS
){
    LocalSkyTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = { CustomNavBar(navController = navController) },

                ) { _ ->

                NavigationGraph(navController = navController, database, weatherViewModelLS)
            }
        }

    }
}