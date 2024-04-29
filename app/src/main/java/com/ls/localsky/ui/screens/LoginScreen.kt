package com.ls.localsky.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.ls.localsky.DatabaseLS
import com.ls.localsky.R
import com.ls.localsky.ui.app.App
import com.ls.localsky.ui.app.LocalSkyAppRouter
import com.ls.localsky.ui.app.Screen
import com.ls.localsky.ui.components.ButtonComponent
import com.ls.localsky.ui.components.ClickableText
import com.ls.localsky.ui.components.DividerTextComponent
import com.ls.localsky.ui.components.NormalTextInput
import com.ls.localsky.ui.components.PasswordInput
import com.ls.localsky.ui.components.TitleText
import com.ls.localsky.viewmodels.UserViewModelLS

@Composable
fun LoginScreen(
    context: Context,
    database: DatabaseLS,
    userViewModelLS: UserViewModelLS
) {

    val emailValue = remember {
        mutableStateOf("")
    }
    val passwordValue = remember {
        mutableStateOf("")
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(28.dp)
    ){
        Column(modifier = Modifier.fillMaxSize()){

            TitleText(value = "Login to " + stringResource(R.string.app_name))
            Spacer(modifier=Modifier.height(50.dp))
            // Text input fields
            // Email
            NormalTextInput(
                labelValue = stringResource(id = R.string.email),
                emailValue,
                Icons.Filled.Mail,
                KeyboardType.Email,
                "Email"
            )
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
//                            Toast.makeText(
//                                context,
//                                "Debug: User Signed in",
//                                Toast.LENGTH_SHORT
//                            ).show()
                            database.getUserByID(
                                it.uid,
                                { _, user ->
                                    userViewModelLS.setCurrentUser(user!!)

                                },
                                {
                                    Log.d("Login", "Error Getting User")
                                }
                            )

                            LocalSkyAppRouter.changeApp(App.Main)
                            LocalSkyAppRouter.navigateTo(Screen.WeatherScreen)
                        },
                        {
                            Log.d("Login", it.toString())
                            if(it is FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(
                                    context,
                                    "Email or password is incorrect",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else{
                                Toast.makeText(
                                    context,
                                    "There was an error signing in try again soon.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                    )
                }
            }
            Spacer(modifier=Modifier.height(10.dp))
            // Switch to Register Screen
            DividerTextComponent()
            ClickableText(
                "Don't have an account? ",
                "Register Here",
                onTextSelected = {
                    LocalSkyAppRouter.navigateTo(Screen.RegisterScreen)
                }
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 16.dp, bottom = 16.dp),
                contentAlignment = Alignment.BottomEnd
            ){
                ClickableText(
                    nonHighlightedText = "",
                    highlightedText = "Continue as guest"
                ) {
                    LocalSkyAppRouter.changeApp(App.Main)
                    LocalSkyAppRouter.navigateTo(Screen.WeatherScreen)
                }
            }

        }

    }

}

/**
 * Checks all fields provided to see if they are valid using [checkIfFieldIsValid]
 * @param emailField The string element from the email text edit field
 * @param passwordField The string element from the password text edit field
 * @return Boolean
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
 * Checks if the given text field is blank
 * and if not it displays a toast with the given error message
 * @param context Application context used to display the toast
 * @param field The string field that is to be checked
 * @param errorMessage A error message that describes the problem to the user
 * @return Boolean
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