package com.example.nutrifind.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.nutrifind.data.SettingsManager
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun NutriFindTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val settings by SettingsManager.settings.collectAsState()
    
    val useDarkTheme = remember(settings) {
        settings.useSystemTheme && darkTheme || 
        (!settings.useSystemTheme && settings.isDarkTheme)
    }
    
    val accentColor = remember(settings.accentColor) {
        try {
            Color(android.graphics.Color.parseColor(settings.accentColor))
        } catch (e: Exception) {
            if (useDarkTheme) Purple80 else Purple40
        }
    }
    
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (useDarkTheme) dynamicDarkColorScheme(context) 
            else dynamicLightColorScheme(context)
        }
        useDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }.copy(primary = accentColor)
    
    val view = LocalView.current
    val window = (view.context as? Activity)?.window
    
    LaunchedEffect(useDarkTheme, colorScheme.primary) {
        window?.let { window ->
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !useDarkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}