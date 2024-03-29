package com.ls.localsky

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ls.localsky.ui.app.LocalSkyApp

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
