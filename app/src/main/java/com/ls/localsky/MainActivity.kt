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
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.ls.localsky.sensors.TemperatureSensor
import com.ls.localsky.viewmodels.SensorViewModelLS

class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var cacheLS: CacheLS
    private lateinit var database: DatabaseLS

    private lateinit var userViewModel: UserViewModelLS
    private lateinit var weatherViewModel: WeatherViewModelLS
    private lateinit var userReportViewModel: UserReportViewModelLS
    private lateinit var sensorViewModel: SensorViewModelLS


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = DatabaseLS()
        cacheLS = CacheLS(this)
        weatherViewModel = ViewModelProvider(this)[WeatherViewModelLS::class.java]
        userViewModel = ViewModelProvider(this)[UserViewModelLS::class.java]
        userReportViewModel = ViewModelProvider(this)[UserReportViewModelLS::class.java]
        sensorViewModel = ViewModelProvider(this)[SensorViewModelLS::class.java]

        weatherViewModel.getWeatherData(cacheLS)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        startTempSensor()
        checkPerms()

        getCurrentLocationAndUpdateWeatherViewModel()

        setScreenActions()

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
                                database = database,
                                weatherViewModel = weatherViewModel,
                                cache = cacheLS,
                                userViewModel = userViewModel,
                                userReportViewModel = userReportViewModel,
                                sensorViewModel = sensorViewModel
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

    private fun startTempSensor(){
        TemperatureSensor.getInstance(this).run {
            startListening()
            setOnSensorValuesChangedListener {
                sensorViewModel.setAmbientTemp(it[0])
                Log.d("TEMPERATURE", "temp = ${sensorViewModel.getAmbientTempC()}")
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocationAndUpdateWeatherViewModel() {

        checkPerms()

        startService(Intent(this, LocationService::class.java))

        // Get last known location
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                // Update the WeatherViewModelLS with the current location
                val latLng = LatLng(location.latitude, location.longitude)
                userViewModel.setCurrentUserLocation(latLng)
                weatherViewModel.setCoordinate(latLng)

                // Call the function to get weather data
                weatherViewModel.getWeatherData(cacheLS)
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun setScreenActions(){
        checkPerms()
        Screen.WeatherScreen.onCLick = {
            weatherViewModel.getWeatherData(cacheLS)
        }
        Screen.MapScreen.onCLick = {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                val latlong = LatLng(location.latitude, location.longitude)
                database.getAllUserReports (latlong){
                    it?.let {
                        Log.d("UserReports", "Getting user reports")
                        userViewModel.setCurrentUserLocation(latlong)
                        userReportViewModel.setUserReports(it, database)
                    }
                }
            }
        }
    }

    private fun checkPerms(){
        // Check for permissions before requesting location
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            // Get the permissions
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                0
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        TemperatureSensor.getInstance(this).stopListening()
    }
}

