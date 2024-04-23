package com.ls.localsky.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ls.localsky.DatabaseLS
import com.ls.localsky.ui.app.App
import com.ls.localsky.ui.app.LocalSkyAppRouter
import com.ls.localsky.ui.app.Screen

@Composable
fun SettingsScreen(
    database: DatabaseLS
){
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
            Column (
                modifier = Modifier.fillMaxSize()
            ){
                Button(
                    modifier = Modifier
                        .align(Alignment.End),
                    onClick = {
                        database.signOut()
                        LocalSkyAppRouter.navigateTo(Screen.LoginScreen)
                        LocalSkyAppRouter.changeApp(App.Login)
                    }
                ){
                    Text(text = "Sign Out")
                }
            }
    }
}