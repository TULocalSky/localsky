package com.ls.localsky.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF3498db),
    secondary = Color(0xFF2c3e50),
    tertiary = Color.White,
    primaryContainer = Color(0xFF3498db),
    secondaryContainer = Color(0xFF3498db),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color.White,
//    onSurface = Color.White,
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF3498db),
    secondary = Color(0xFFFFFFFF),
    tertiary = Color.White,
    primaryContainer = Color(0xFF3498db),
    secondaryContainer = Color(0xFFC8F4F9),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color.White,
//    onSurface = Color.White,


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