package com.ls.localsky.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ls.localsky.DatabaseLS
import com.ls.localsky.models.User
import com.ls.localsky.ui.app.App
import com.ls.localsky.ui.app.LocalSkyAppRouter
import com.ls.localsky.ui.app.Screen
import com.ls.localsky.ui.components.baselineHeight
import com.ls.localsky.viewmodels.UserViewModelLS

@Composable
fun SettingsScreen(
    modifier: Modifier,
    database: DatabaseLS,
    userViewModel: UserViewModelLS
){
    Box (
        modifier = modifier
    ){
        Surface{
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {

                SettingsTopBar()
                Spacer(modifier = Modifier.padding(20.dp))
                CurrentUserDisplay(userViewModel)
                Spacer(modifier = Modifier.padding(20.dp))
                Divider()
                SettingsDisplay()
            }
        }
    }

    SignoutButton(
        modifier = modifier,
        database = database,
        userViewModel = userViewModel
    )
}

@Composable
fun SettingsDisplay() {
}

@Composable
fun CurrentUserDisplay(
    userViewModel: UserViewModelLS
){
    Card (
    ){
        NameAndEmail(user = userViewModel.getCurrentUser())
    }
}

@Composable
fun SettingsTopBar(){
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Spacer(modifier = Modifier.padding(5.dp))
            Box(
                contentAlignment = Alignment.Center
            ){
                Row {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Settings"
                    )
                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            Spacer(modifier = Modifier.padding(5.dp))
        }
    }

}

@Composable
private fun NameAndEmail(
    user: User?
) {
    user?.let {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Name(
                user,
                modifier = Modifier.baselineHeight(32.dp)
            )
            Email(
                user,
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .baselineHeight(24.dp)
            )
        }
    }
}

@Composable
private fun Name(user: User, modifier: Modifier = Modifier) {
    user.firstName?.let {
        Text(
            text = user.firstName + " " + user.lastName,
            modifier = modifier,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
private fun Email(user: User, modifier: Modifier = Modifier) {
    user.email?.let {
        Text(
            text = user.email,
            modifier = modifier,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun SignoutButton(
    modifier: Modifier,
    database: DatabaseLS,
    userViewModel: UserViewModelLS
){
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        ExtendedFloatingActionButton(
            modifier = Modifier.padding(10.dp),
            onClick = {
                database.signOut()
                userViewModel.clearCurrentUser()
                LocalSkyAppRouter.navigateTo(Screen.LoginScreen)
                LocalSkyAppRouter.changeApp(App.Login)
            },
            icon = {
                if(userViewModel.getCurrentUser() != null){
                    Icon(Icons.AutoMirrored.Filled.Logout, "Sign Out")
                } else{
                    Icon(Icons.AutoMirrored.Filled.Login, "Login")
                }
            },
            text = {
                if(userViewModel.getCurrentUser() != null){
                    Text(text = "Sign Out")
                } else{
                    Text(text = "Login")
                }
            }
        )
    }

}
