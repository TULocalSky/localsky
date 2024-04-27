package com.ls.localsky

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.ls.localsky.ui.screens.LoginScreen
import com.ls.localsky.ui.screens.RegisterScreen
import com.ls.localsky.ui.theme.LocalSkyTheme
import com.ls.localsky.viewmodels.UserViewModelLS
import org.junit.Rule
import org.junit.Test

class UserInputTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val activity by lazy { composeTestRule.activity }

    private val database = DatabaseLS()
    private val userViewModel = UserViewModelLS()

    @Test
    fun loginScreenTest(){
        composeTestRule.setContent {
            LocalSkyTheme {
                LoginScreen(
                    context = LocalContext.current,
                    database = database,
                    userViewModelLS = userViewModel
                )
            }
        }

        composeTestRule.onNodeWithContentDescription(activity.getString(R.string.email)).performTextInput("tulocalsky@gmail.com")
        composeTestRule.onNodeWithContentDescription(activity.getString(R.string.password)).performTextInput("Test12345")
        composeTestRule.onNodeWithText(activity.getString(R.string.login)).performClick()

    }

}