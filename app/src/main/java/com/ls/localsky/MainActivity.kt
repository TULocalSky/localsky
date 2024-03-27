package com.ls.localsky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ls.localsky.app.LocalSkyApp
import com.ls.localsky.loginScreen.LoginScreen
import com.ls.localsky.ui.theme.LocalSkyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Example
        //val url = "/forecast/$api_key/{lat_and_long_or_time}"
        setContent {
            LocalSkyApp()
            }
        }
    }
