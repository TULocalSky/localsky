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
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.ls.localsky.sensors.RelativeHumiditySensor
import com.ls.localsky.sensors.TemperatureSensor
import com.ls.localsky.viewmodels.SensorViewModelLS
import java.util.concurrent.TimeUnit

val REQUESTING_LOCATION_UPDATES_KEY = "location_update_key"

class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    private lateinit var cacheLS: CacheLS
    private lateinit var database: DatabaseLS

    private lateinit var userViewModel: UserViewModelLS
    private lateinit var weatherViewModel: WeatherViewModelLS
    private lateinit var userReportViewModel: UserReportViewModelLS
    private lateinit var sensorViewModel: SensorViewModelLS

    private var requestingLocationUpdates = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPerms()

        updateValuesFromBundle(savedInstanceState)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations){
                    val latLng = LatLng(location.latitude, location.longitude)
                    userViewModel.setCurrentUserLocation(latLng)
                    weatherViewModel.setCoordinate(latLng)
                }
            }
        }

        database = DatabaseLS()
        cacheLS = CacheLS(this)
        weatherViewModel = ViewModelProvider(this)[WeatherViewModelLS::class.java]
        userViewModel = ViewModelProvider(this)[UserViewModelLS::class.java]
        userReportViewModel = ViewModelProvider(this)[UserReportViewModelLS::class.java]
        sensorViewModel = ViewModelProvider(this)[SensorViewModelLS::class.java]

        startTempSensor()
        startRelativeHumiditySensor()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getCurrentLocationAndUpdateWeatherViewModel()

        weatherViewModel.getWeatherData(cacheLS)

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

    private fun startRelativeHumiditySensor(){
        RelativeHumiditySensor.getInstance(this).run {
            startListening()
            setOnSensorValuesChangedListener {
                sensorViewModel.setRelativeHumidity(it[0])
                Log.d("HUMIDITY", "humidity = ${sensorViewModel.getRelativeHumidity()}")
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
//                weatherViewModel.getWeatherData(cacheLS)
            }
        }

    }

    @SuppressLint("MissingPermission")
    fun setScreenActions(){
        checkPerms()
        Screen.WeatherScreen.onCLick = {
//            weatherViewModel.getWeatherData(cacheLS)
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

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, requestingLocationUpdates)
        super.onSaveInstanceState(outState)
    }

    private fun updateValuesFromBundle(savedInstanceState: Bundle?) {
        savedInstanceState ?: return

        // Update the value of requestingLocationUpdates from the Bundle.
        if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
            requestingLocationUpdates = savedInstanceState.getBoolean(
                REQUESTING_LOCATION_UPDATES_KEY)
        }
    }

    override fun onResume() {
        super.onResume()
        if (requestingLocationUpdates) startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        checkPerms()
        val locationRequest = LocationRequest.create().apply {
            interval = TimeUnit.SECONDS.toMillis(60)

            fastestInterval = TimeUnit.SECONDS.toMillis(30)

            maxWaitTime = TimeUnit.MINUTES.toMillis(2)

            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}

