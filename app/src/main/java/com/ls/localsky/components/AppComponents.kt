package com.ls.localsky.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ls.localsky.R

@Composable

fun TitleText(value: String) {
    Text(
        text= value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp),
        style = TextStyle(
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center
        )
    )
}

@Composable
fun EmailInput(labelValue:String) {

    val emailValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = {Text(text = labelValue)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.LightGray,
            focusedLabelColor = Color.LightGray,
            cursorColor = Color.White
        ),
        keyboardOptions = KeyboardOptions.Default,
        value = emailValue.value,
        onValueChange = {
            emailValue.value = it
        },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Mail, contentDescription = "", tint = Color.LightGray)
        }
    )

}

@Composable
fun PasswordInput(labelValue:String) {

    val passwordValue = remember {
        mutableStateOf("")
    }

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp)),
        label = {Text(text = labelValue)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.LightGray,
            focusedLabelColor = Color.LightGray,
            cursorColor = Color.White
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        value = passwordValue.value,
        onValueChange = {
            passwordValue.value = it
        },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Lock, contentDescription = "", tint = Color.LightGray)
        },

        trailingIcon = {
            val iconImage = if(passwordVisible.value){
                Icons.Filled.Visibility
            }else {
                Icons.Filled.VisibilityOff
            }

            var description = if(passwordVisible.value){
                stringResource(id = R.string.hide_password)
            }else {
                stringResource(id= R.string.show_password)
            }

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value}) {
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        visualTransformation = if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
    )
}


@Composable
fun FirstNameInput(labelValue: String) {
    val firstNameValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = {Text(text = labelValue)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.LightGray,
            focusedLabelColor = Color.LightGray,
            cursorColor = Color.White
        ),
        keyboardOptions = KeyboardOptions.Default,
        value = firstNameValue.value,
        onValueChange = {
            firstNameValue.value = it
        },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Person, contentDescription = "", tint = Color.LightGray)
        }
    )
}
@Composable
fun LastNameInput(labelValue: String) {
    val lastNameValue = remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = {Text(text = labelValue)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.LightGray,
            focusedLabelColor = Color.LightGray,
            cursorColor = Color.White
        ),
        keyboardOptions = KeyboardOptions.Default,
        value = lastNameValue.value,
        onValueChange = {
            lastNameValue.value = it
        },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Person, contentDescription = "", tint = Color.LightGray)
        }
    )
}
@Composable
fun SignInButton() {

}
@Composable
fun RegisterButton() {

}

