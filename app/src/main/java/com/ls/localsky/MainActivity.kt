package com.ls.localsky

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.ls.localsky.services.LocationService
import com.ls.localsky.ui.app.App
import com.ls.localsky.ui.app.LocalSkyApp
import com.ls.localsky.ui.app.LocalSkyAppRouter
import com.ls.localsky.ui.app.LocalSkyLoginApp
import com.ls.localsky.ui.app.Screen
import com.ls.localsky.ui.theme.LocalSkyTheme
import com.ls.localsky.viewmodels.UserReportViewModelLS
import com.ls.localsky.viewmodels.UserViewModelLS
import com.ls.localsky.viewmodels.WeatherViewModelLS
import android.Manifest
import com.ls.localsky.util.sensors.TemperatureSensor
import com.ls.localsky.viewmodels.SensorViewModelLS

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val svm = SensorViewModelLS()

        TemperatureSensor.getInstance(this).run {
            startListening()
            setOnSensorValuesChangedListener {
                svm.setAmbientTemp(it[0])
                Log.d("TEMPERATURE", "temp = ${svm.getAmbientTempC()}")
            }
        }

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            0
        )

        startService(Intent(this, LocationService::class.java))
        
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
                    userReportViewModel.setUserReports(it, database)
                }
            }
        }
        setContent {
            LocalSkyTheme {

//                val ts = TemperatureSensor(this)
//                if(ts.doesSensorExist){
//                    Log.d("TEMPERATURE", "temp = ${ts.getTempC()}C")
//                }else{
//                    Log.d("TEMPERATURE", "Device cannot track ambient temp")
//                }

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

    override fun onDestroy() {
        super.onDestroy()

        TemperatureSensor.getInstance(this).stopListening()
    }
}

