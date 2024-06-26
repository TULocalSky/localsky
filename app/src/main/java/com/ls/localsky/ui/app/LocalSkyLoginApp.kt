package com.ls.localsky.ui.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.ls.localsky.DatabaseLS
import com.ls.localsky.ui.screens.LoginScreen
import com.ls.localsky.ui.screens.RegisterScreen
import com.ls.localsky.viewmodels.UserViewModelLS


@Composable
fun LocalSkyLoginApp(
    database: DatabaseLS,
    userViewModel: UserViewModelLS
){
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Crossfade(targetState = LocalSkyAppRouter.currentScreen, label = "") { currentState ->
            when(currentState.value) {
                is Screen.LoginScreen -> {
                    LoginScreen(
                        LocalContext.current,
                        database,
                        userViewModel
                    )
                }
                is Screen.RegisterScreen -> {
                    RegisterScreen(
                        LocalContext.current,
                        database,
                        userViewModel
                    )
                }
                else -> {

                }
            }
        }

    }
}