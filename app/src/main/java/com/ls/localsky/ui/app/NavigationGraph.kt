package com.ls.localsky.ui.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ls.localsky.CacheLS
import com.ls.localsky.DatabaseLS
import com.ls.localsky.ui.screens.LoginScreen
import com.ls.localsky.ui.screens.MapScreen
import com.ls.localsky.ui.screens.RegisterScreen
import com.ls.localsky.ui.screens.SettingsScreen
import com.ls.localsky.ui.screens.WeatherScreen
import com.ls.localsky.viewmodels.SensorViewModelLS
import com.ls.localsky.viewmodels.UserReportViewModelLS
import com.ls.localsky.viewmodels.UserViewModelLS
import com.ls.localsky.viewmodels.WeatherViewModelLS

@Composable
fun NavigationGraph(
    modifier: Modifier,
    navController: NavHostController,
    databaseLS: DatabaseLS,
    cache: CacheLS,
    weatherViewModel: WeatherViewModelLS,
    userViewModel: UserViewModelLS,
    userReportViewModel: UserReportViewModelLS,
    sensorViewModel: SensorViewModelLS,
) {
    NavHost(navController, startDestination = Screen.WeatherScreen.screen_route) {
        composable(Screen.MapScreen.screen_route) {
            MapScreen(
                modifier = modifier,
                database = databaseLS,
                userViewModel = userViewModel,
                userReportViewModel = userReportViewModel,
                sensorViewModel = sensorViewModel,
            )
        }
        composable(Screen.SettingsScreen.screen_route) {
            SettingsScreen(
                databaseLS
            )
        }
        composable(Screen.WeatherScreen.screen_route) {
            WeatherScreen(
                viewModelLS = weatherViewModel,
                cache = cache,
                modifier = modifier,
                userViewModel = userViewModel
            )
        }
        composable(Screen.RegisterScreen.screen_route){
            RegisterScreen(
                LocalContext.current,
                databaseLS,
                userViewModel
            )
        }
        composable(Screen.LoginScreen.screen_route){
            LoginScreen(
                context = LocalContext.current,
                database = databaseLS,
                userViewModel
            )
        }


    }
}