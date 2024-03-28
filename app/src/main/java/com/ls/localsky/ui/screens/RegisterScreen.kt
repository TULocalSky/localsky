package com.ls.localsky.ui.screens

import android.content.Context
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
import androidx.compose.material.icons.filled.Person
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
import com.ls.localsky.ui.components.ClickableLoginText
import com.ls.localsky.ui.components.DividerTextComponent
import com.ls.localsky.ui.components.NormalTextInput
import com.ls.localsky.ui.components.PasswordInput
import com.ls.localsky.ui.components.TitleText

@Composable
fun RegisterScreen(
    context: Context,
    database: DatabaseLS
) {

    val firstNameValue = remember {
        mutableStateOf("")
    }
    val lastNameValue = remember {
        mutableStateOf("")
    }
    val passwordValue = remember {
        mutableStateOf("")
    }
    val emailValue = remember {
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

            TitleText(value = "Register to " + stringResource(R.string.app_name))
            Spacer(modifier=Modifier.height(50.dp))
            // Text input fields
            // First Name
            NormalTextInput(labelValue = stringResource(id = R.string.first_name), firstNameValue, Icons.Filled.Person)
            // Last name
            NormalTextInput(labelValue = stringResource(id = R.string.last_name), lastNameValue, Icons.Filled.Person)
            // Email
            NormalTextInput(labelValue = stringResource(id = R.string.email), emailValue, Icons.Filled.Mail)
            PasswordInput(labelValue = stringResource(id = R.string.password), passwordValue)
            Spacer(modifier=Modifier.height(50.dp))
            //Buttons
            Spacer(modifier=Modifier.height(10.dp))
            ButtonComponent(value = stringResource(id = R.string.register)){
                if(checkInputFields(
                    context,
                    firstNameValue.value,
                    lastNameValue.value,
                    passwordValue.value,
                    emailValue.value
                )){

                }
            }
            Spacer(modifier=Modifier.height(10.dp))
            //Login divider
            DividerTextComponent()
            ClickableLoginText(onTextSelected = {
                LocalSkyAppRouter.navigateTo(Screen.LoginScreen)
            })

        }

    }

}

@Preview
@Composable
fun DefaultPreviewOfRegisterScreen() {
    RegisterScreen(LocalContext.current,  DatabaseLS())
}

/**
 *
 */
fun checkInputFields(
    context: Context,
    firstNameField: String,
    lastNameField: String,
    emailField: String,
    passwordField: String
): Boolean{
    return checkIfFieldIsValid(
        context,
        firstNameField,
        "Please enter a value for First Name"
    ) &&
    checkIfFieldIsValid(
        context,
        lastNameField,
        "Please enter a value for Last Name"
    ) &&
    checkIfFieldIsValid(
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
fun checkIfFieldIsValid(
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