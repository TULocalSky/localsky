package com.ls.localsky

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ls.localsky.ui.theme.LocalSkyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Example
        //val url = "/forecast/$api_key/{lat_and_long_or_time}"
        val db = DatabaseLS()

        db.createUser(
            "TestFirstName",
            "TestLastName",
            "test@test.com",
            "TestPassword",
            {
                Log.d("User", it.email!!)
            },
            {
                Log.d("Except", it.toString())
            }
        )

        setContent {
            LocalSkyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                }
            }
        }
    }
}