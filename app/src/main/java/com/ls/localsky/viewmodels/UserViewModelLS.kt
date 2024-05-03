package com.ls.localsky.viewmodels

import android.location.Location
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.ls.localsky.models.User
import com.ls.localsky.services.LocationRepository

class UserViewModelLS : ViewModel() {

    private var currentUser = mutableStateOf<User?>(User())

    fun getCurrentUser(): User?{
        return  currentUser.value
    }

    fun setCurrentUser(user: User){
        currentUser.value = user
    }

    private val currentUserLocation = mutableStateOf<LatLng?>(null)

    /**
     * Gets the location of the user contained in the view model
     * @return [LiveData] containing a [LatLng]
     */
    fun getCurrentUserLocation(): MutableState<LatLng?> {
        LocationRepository.currentLocation.value?.let {
            currentUserLocation.value = it
        }
        Log.d(TAG,currentUserLocation.value.toString())
        return currentUserLocation
    }

    /**
     * Sets the users current location in the view model
     * @param newLocation A [LatLng] that represents the new location for the user
     */
    fun setCurrentUserLocation(newLocation: LatLng){
        currentUserLocation.value = newLocation
    }

    fun clearCurrentUser() {
        currentUser.value = null
    }

    companion object{
        val TAG = "UserViewModel"
    }
}