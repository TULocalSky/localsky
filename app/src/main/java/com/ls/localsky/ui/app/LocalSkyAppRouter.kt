package com.ls.localsky.ui.app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(var title: String, var icon: ImageVector, var screen_route: String, val showBottomBar: Boolean) {
    object LoginScreen : Screen("Login",Icons.Default.Person,"login", false)
    object RegisterScreen : Screen("CreateAccount",Icons.Default.Person,"create_account", false)
    object MapScreen : Screen("Map", Icons.Default.Place,"map", true)
    object SettingsScreen : Screen("Settings",Icons.Default.Settings,"settings", true)
}

sealed class App{
    object Login : App()
    object Main : App()
}

object LocalSkyAppRouter {
    val currentApp : MutableState<App> = mutableStateOf(App.Login)

    val currentScreen : MutableState<Screen> = mutableStateOf(Screen.LoginScreen)

    //Navigation from one screen to the next
    fun navigateTo(destination: Screen) {
        currentScreen.value = destination
    }
    fun changeApp(app: App){
        currentApp.value = app
    }

}