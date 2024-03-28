package com.ls.localsky.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ls.localsky.R
import com.ls.localsky.ui.app.LocalSkyAppRouter
import com.ls.localsky.ui.app.Screen
import com.ls.localsky.ui.components.ButtonComponent
import com.ls.localsky.ui.components.ClickableRegisterText
import com.ls.localsky.ui.components.DividerTextComponent
import com.ls.localsky.ui.components.EmailInput
import com.ls.localsky.ui.components.PasswordInput
import com.ls.localsky.ui.components.TitleText

@Composable
fun LoginScreen() {

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ){
        Column(modifier = Modifier.fillMaxSize()){

            TitleText(value = "Login to " + stringResource(R.string.app_name))
            Spacer(modifier=Modifier.height(50.dp))
            //text input fields
            EmailInput(labelValue = stringResource(id = R.string.email))
            PasswordInput(labelValue = stringResource(id = R.string.password))
            Spacer(modifier=Modifier.height(50.dp))
            //Buttons
            ButtonComponent(value = stringResource(id = R.string.login))
            Spacer(modifier=Modifier.height(10.dp))
            //Login with google
            DividerTextComponent()
            ClickableRegisterText(onTextSelected = {
                LocalSkyAppRouter.navigateTo(Screen.RegisterScreen)
            })

        }

    }

}

@Preview
@Composable
fun DefaultPreviewOfLoginScreen() {
    LoginScreen()
}