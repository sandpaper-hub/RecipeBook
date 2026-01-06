package com.example.recipebook.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import com.example.recipebook.domain.model.ThemeMode


private val DarkColorScheme = darkColorScheme(
    primary = GreenAccent,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    onPrimary = Color.White,
    inversePrimary = DarkModeBodyColor,
    surface = Color.Red,
    onSurfaceVariant = DarkModeInputColor,
    background = DarkModeBackgroundColor,
    onBackground = Color.White,
    error = DangerColor,
    onTertiaryContainer = DarkModeInputColor
)

private val LightColorScheme = lightColorScheme(
    primary = GreenAccent,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    onPrimary = Color.Black,
    inversePrimary = TitleGray,
    onSurfaceVariant = InputColor,
    background = Color.White,
    onBackground = MainTextColor,
    error = DangerColor,
    onTertiaryContainer = LightGreen

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun RecipeBookTheme(
    mode: ThemeMode,
    content: @Composable () -> Unit
) {
    val isDarkTheme = when (mode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }
    val colorScheme = if (isDarkTheme) DarkColorScheme else LightColorScheme
    val locale = LocalConfiguration.current.locales[0]

    MaterialTheme(
        colorScheme = colorScheme,
        typography = rememberAppTypography(locale),
        content = content
    )
}