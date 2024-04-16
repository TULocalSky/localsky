package com.ls.localsky.ui.app

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ls.localsky.CacheLS
import com.ls.localsky.DatabaseLS
import com.ls.localsky.ui.components.CustomNavBar
import com.ls.localsky.ui.theme.LocalSkyTheme
import com.ls.localsky.viewmodels.UserViewModelLS
import com.ls.localsky.viewmodels.WeatherViewModelLS

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LocalSkyApp(
    database: DatabaseLS,
    weatherViewModel: WeatherViewModelLS,
    cache: CacheLS,
    userViewModel: UserViewModelLS
){
    LocalSkyTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = { CustomNavBar(navController = navController) },

                ) { innerPadding ->

                NavigationGraph(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController,
                    databaseLS = database,
                    weatherViewModel = weatherViewModel,
                    cache = cache,
                    userViewModel = userViewModel
                )
            }
        }

    }
}