package com.ls.localsky.ui.screens

import android.content.Context
import android.graphics.drawable.Icon
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ls.localsky.DatabaseLS
import com.ls.localsky.R
import com.ls.localsky.ui.app.LocalSkyAppRouter
import com.ls.localsky.ui.app.Screen
import com.ls.localsky.ui.components.ButtonComponent
import com.ls.localsky.ui.components.ClickableRegisterText
import com.ls.localsky.ui.components.DividerTextComponent
import com.ls.localsky.ui.components.NormalTextInput
import com.ls.localsky.ui.components.PasswordInput
import com.ls.localsky.ui.components.TitleText

@Composable
fun LoginScreen(
    context: Context,
    database: DatabaseLS
) {

    val emailValue = remember {
        mutableStateOf("")
    }
    val passwordValue = remember {
        mutableStateOf("")
    }

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
            // Text input fields
            // Email
            NormalTextInput(labelValue = stringResource(id = R.string.email), emailValue, Icons.Filled.Mail)
            PasswordInput(labelValue = stringResource(id = R.string.password), passwordValue)
            Spacer(modifier=Modifier.height(50.dp))
            //Buttons
            ButtonComponent(value = stringResource(id = R.string.login)){
                if(checkInputFields(
                        context,
                        passwordValue.value,
                        emailValue.value
                    )){
                    database.signIn(
                        emailValue.value,
                        passwordValue.value,
                        {
                            Toast.makeText(
                                context,
                                "Debug: User Signed in",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Put nav here
                            // LocalSkyAppRouter.navigateTo(Screen.WeatherScreen)
                        },
                        {
                            Toast.makeText(
                                context,
                                "There was an error signing in try again soon.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
            Spacer(modifier=Modifier.height(10.dp))
            // Switch to Register Screen
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
    LoginScreen(LocalContext.current, DatabaseLS())
}

/**
 *
 */
private fun checkInputFields(
    context: Context,
    emailField: String,
    passwordField: String
): Boolean{
    return checkIfFieldIsValid(
                context,
                emailField,
                "Please enter a value for Email"
            ) &&
            checkIfFieldIsValid(
                context,
                passwordField,
                "Please enter a value for Password"
            )
}


/**
 *
 */
private fun checkIfFieldIsValid(
    context: Context,
    field: String,
    errorMessage: String
): Boolean{
    if(field.isNotBlank()){
        return true
    }
    Toast.makeText(
        context,
        errorMessage,
        Toast.LENGTH_SHORT
    ).show()
    return false
}