package com.ls.localsky

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.toObject
import com.ls.localsky.models.User
import com.ls.localsky.ui.app.App
import com.ls.localsky.ui.app.LocalSkyApp
import com.ls.localsky.ui.app.LocalSkyAppRouter
import com.ls.localsky.ui.app.LocalSkyLoginApp
import com.ls.localsky.ui.app.Screen
import com.ls.localsky.ui.theme.LocalSkyTheme
import com.ls.localsky.viewmodels.UserViewModelLS
import com.ls.localsky.viewmodels.WeatherViewModelLS

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val database = DatabaseLS()
        val cacheLS = CacheLS(this)
        val weatherViewModel = ViewModelProvider(this)[WeatherViewModelLS::class.java]
        val userViewModel = ViewModelProvider(this)[UserViewModelLS::class.java]
        weatherViewModel.getWeatherData(cacheLS)

        setContent {
            LocalSkyTheme {

                Log.d("Logged in user", database.getCurrentUser()!!.email!!)
                if(database.getCurrentUser() != null){
                    LocalSkyAppRouter.changeApp(App.Main)
                    LocalSkyAppRouter.navigateTo(Screen.WeatherScreen)
                    database.getUserByID(
                        database.getCurrentUser()!!.uid,
                        {document ->
                                userViewModel.setCurrentUser(document!!.toObject<User>())
                                Log.d("Login", "Got User ${document.toObject<User>()}")

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
                                database,
                                weatherViewModel,
                                cacheLS,
                                userViewModel
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
}

