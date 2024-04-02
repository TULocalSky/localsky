package com.ls.localsky.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.type.LatLng

class LocationViewModelLS {


    private val currentUserLocation by lazy{
        MutableLiveData<LatLng>()
    }

    /**
     * Gets the location of the user contained in the view model
     * @return [LiveData] containing a [LatLng]
     */
    fun getCurrentUserLocation(): LiveData<LatLng>{
        return currentUserLocation
    }

    /**
     * Sets the users current location in the view model
     * @param newLocation A [LatLng] that represents the new location for the user
     */
    fun setCurrentUserLocation(newLocation: LatLng){
        currentUserLocation.value = newLocation
    }

}