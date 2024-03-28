package com.ls.localsky.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.type.LatLng

class LocationViewModelLS {

    private val currentUserLocation by lazy{
        MutableLiveData<LatLng>()
    }

    /**
     *
     */
    fun getCurrentUserLocation(): LiveData<LatLng>{
        return currentUserLocation
    }

    /**
     *
     */
    fun setCurrentUserLocation(newLocation: LatLng){
        currentUserLocation.value = newLocation
    }

}