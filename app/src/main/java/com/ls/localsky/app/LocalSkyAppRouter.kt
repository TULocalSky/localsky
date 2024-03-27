package com.ls.localsky.app

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen() {
    object LoginScreen : Screen()
    object RegisterScreen : Screen()
}

object LocalSkyAppRouter {

    val currentScreen : MutableState<Screen> = mutableStateOf(Screen.LoginScreen)

    //Navigation from one screen to the next
    fun navigateTo(destination: Screen) {
        currentScreen.value = destination
    }

}