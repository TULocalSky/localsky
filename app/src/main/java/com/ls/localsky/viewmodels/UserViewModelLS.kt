package com.ls.localsky.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ls.localsky.models.User

class UserViewModelLS : ViewModel() {

    private var currentUser = mutableStateOf(User())

    fun getCurrentUser(): User{
        return  currentUser.value
    }

    fun setCurrentUser(user: User){
        currentUser.value = user
    }

    companion object{
        val TAG = "UserViewModel"
    }
}