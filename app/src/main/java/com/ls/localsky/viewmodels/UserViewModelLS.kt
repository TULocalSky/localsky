package com.ls.localsky.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.ls.localsky.models.User

class UserViewModelLS : ViewModel() {

    private var currentUser = mutableStateOf(User())

    fun getCurrentUser(): User{
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
    fun getCurrentUserLocation(): LatLng? {
        return currentUserLocation.value
    }

    /**
     * Sets the users current location in the view model
     * @param newLocation A [LatLng] that represents the new location for the user
     */
    fun setCurrentUserLocation(newLocation: LatLng){
        currentUserLocation.value = newLocation
    }

    companion object{
        val TAG = "UserViewModel"
    }
}