package com.ls.localsky.services

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.IBinder
import android.util.Log
import com.google.android.gms.maps.model.LatLng

class LocationService : Service() {

    private lateinit var locationManager: LocationManager
    private var locationListener: LocationListener? = null
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationListener = LocationListener { location ->
            val latLng = LatLng(location.latitude, location.longitude)
            LocationRepository.updateLocation(latLng)
        }

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                10000L,
                0f,
                locationListener!!
            )
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        locationListener?.let { locationManager.removeUpdates(it) }
    }



}