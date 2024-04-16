package com.ls.localsky

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.lifecycle.ViewModelProvider
import com.ls.localsky.ui.app.App
import com.ls.localsky.ui.app.LocalSkyApp
import com.ls.localsky.ui.app.LocalSkyAppRouter
import com.ls.localsky.ui.app.LocalSkyLoginApp
import com.ls.localsky.ui.app.Screen
import com.ls.localsky.ui.theme.LocalSkyTheme
import com.ls.localsky.viewmodels.UserReportViewModelLS
import com.ls.localsky.viewmodels.UserViewModelLS
import com.ls.localsky.viewmodels.WeatherViewModelLS

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val database = DatabaseLS()
        val cacheLS = CacheLS(this)
        val weatherViewModel = ViewModelProvider(this)[WeatherViewModelLS::class.java]
        val userViewModel = ViewModelProvider(this)[UserViewModelLS::class.java]
        val userReportViewModel = ViewModelProvider(this)[UserReportViewModelLS::class.java]
        weatherViewModel.getWeatherData(cacheLS)
        Screen.WeatherScreen.onCLick = {
            weatherViewModel.getWeatherData(cacheLS)
        }
        Screen.MapScreen.onCLick = {
            database.getAllUserReports {
                it?.let {
                    userReportViewModel.setUserReports(it)
                }
            }
        }
        setContent {
            LocalSkyTheme {

                if(database.getCurrentUser() != null){
                    LocalSkyAppRouter.changeApp(App.Main)
                    LocalSkyAppRouter.navigateTo(Screen.WeatherScreen)
                    database.getUserByID(
                        database.getCurrentUser()!!.uid,
                        {_, user ->
                                userViewModel.setCurrentUser(user!!)
                                Log.d("Login", "Got User $user")

                        },
                        {
                            Log.d("Login", "Error Getting User ${database.getCurrentUser()!!.uid}")
                        }
                    )
                }

                Crossfade(targetState = LocalSkyAppRouter.currentApp, label = "") { currentApp ->
                    when(currentApp.value){
                        App.Main -> {
                            LocalSkyApp(
                                database,
                                weatherViewModel,
                                cacheLS,
                                userViewModel,
                                userReportViewModel
                            )
                        }
                        App.Login -> {
                            LocalSkyLoginApp(
                                database = database,
                                userViewModel
                            )
                        }
                    }

                }

            }
        }
    }
}

