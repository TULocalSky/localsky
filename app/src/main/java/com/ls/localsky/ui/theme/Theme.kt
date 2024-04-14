package com.ls.localsky.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = DarkBlue1,
    secondary = DarkModeBackgroundSurface,
    tertiary = Color.Red,
    primaryContainer = DarkBlue1,
    secondaryContainer = DarkBlue2,
    onPrimary = DarkModeTextColor,
    onSecondary = DarkModeTextColor,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)

private val LightColorScheme = lightColorScheme(
    primary = LightBlue1,
    secondary = LightModeBackgroundSurface,
    tertiary = Color.Red,
    primaryContainer = LightBlue1,
    secondaryContainer = LightBlue2,
    onPrimary = LightModeTextColor,
    onSecondary = Color.White,
    onTertiary = Color.Yellow,
    onBackground = Color.White,
    onSurface = Color.Black,


)

@Composable
fun LocalSkyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {

    val colorScheme =
        if (!darkTheme) {
            LightColorScheme
        } else {
            DarkColorScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}