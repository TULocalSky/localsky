package com.ls.localsky

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import com.ls.localsky.ui.app.App
import com.ls.localsky.ui.app.LocalSkyApp
import com.ls.localsky.ui.app.LocalSkyAppRouter
import com.ls.localsky.ui.app.LocalSkyLoginApp
import com.ls.localsky.ui.theme.LocalSkyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val database = DatabaseLS()
        
        setContent {
            LocalSkyTheme {

                Log.d("Logged in user", database.getCurrentUser().toString())
                if(database.getCurrentUser() != null){
                    LocalSkyAppRouter.changeApp(App.Main)
                }

                Crossfade(targetState = LocalSkyAppRouter.currentApp, label = "") { currentApp ->
                    when(currentApp.value){
                        App.Main -> {
                            LocalSkyApp(database)
                        }
                        App.Login -> {
                            LocalSkyLoginApp(database = database)
                        }
                    }

                }

            }
        }
    }
}

