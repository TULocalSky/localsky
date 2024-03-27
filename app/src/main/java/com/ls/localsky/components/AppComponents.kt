package com.ls.localsky.components


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.rpc.context.AttributeContext.Resource
import com.ls.localsky.R
import com.ls.localsky.ui.theme.Purple80
import com.ls.localsky.ui.theme.PurpleGrey40
import com.ls.localsky.ui.theme.PurpleGrey80

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
fun ButtonComponent(value: String) {
    Button(onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp)
            .background(
                brush = Brush.horizontalGradient(listOf(PurpleGrey40, PurpleGrey80)),
                shape = RoundedCornerShape(25.dp)
            ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }


    }
}

@Composable
fun DividerTextComponent() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){

        Divider(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            color = Color.Gray,
            thickness = 1.dp
        )

        Text(modifier = Modifier.padding(8.dp),
            text= "or",
            fontSize = 16.sp,
            color = Color.Gray)

        Divider(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            color = Color.Gray,
            thickness = 1.dp
        )
    }
}

@Composable
fun ClickableLoginText(onTextSelected: (String) -> Unit) {
    val initText = "Login with your "
    val googleAccountText = "Google Account"

    val annotatedString = buildAnnotatedString {
        append(initText)
        withStyle(style = SpanStyle(color = Color.Blue)) {
            pushStringAnnotation(tag = googleAccountText, annotation = googleAccountText)
            append(googleAccountText)
        }
    }

    ClickableText(text = annotatedString, onClick = { offset ->
        annotatedString.getStringAnnotations(offset, offset)
            .firstOrNull()?.also{ span ->
                Log.d("ClickableLoginText", "{${span.item}}")

                if (span.item == googleAccountText) {
                    onTextSelected(span.item)
                }

            }
    })

}
