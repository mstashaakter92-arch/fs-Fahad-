package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = DiamondCyan,
    onPrimary = DarkNavy900,
    primaryContainer = ElectricViolet,
    onPrimaryContainer = Color.White,
    secondary = AmberOrange,
    onSecondary = DarkNavy900,
    tertiary = BrightGold,
    background = DarkNavy900,
    onBackground = TextPrimaryDark,
    surface = DarkNavy800,
    onSurface = TextPrimaryDark,
    surfaceVariant = DarkNavy700,
    onSurfaceVariant = TextSecondaryDark,
    outline = BorderDark,
  )

private val LightColorScheme =
  lightColorScheme(
    primary = ElectricViolet,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFF3E8FF),
    onPrimaryContainer = ElectricViolet,
    secondary = AmberOrange,
    onSecondary = Color.White,
    tertiary = DiamondBlue,
    background = Color(0xFFF1F5F9),
    onBackground = TextPrimaryLight,
    surface = SurfaceCardLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = Color(0xFFF8FAFC),
    onSurfaceVariant = TextSecondaryLight,
    outline = BorderLight,
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Keep branded gaming palette
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

