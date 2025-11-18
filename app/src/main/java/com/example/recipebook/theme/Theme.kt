package com.example.recipebook.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Typography
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.recipebook.R


private val DarkColorScheme = darkColorScheme(
    primary = Color.White,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    onPrimary = Color.White,
    inversePrimary = DarkModeBodyColor,
    onSurfaceVariant = DarkModeInputColor,
    background = DarkModeBackgroundColor,
    onBackground = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = GreenAccent,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    onPrimary = Color.Black,
    inversePrimary = TitleGray,
    onSurfaceVariant = InputColor,
    background = Color.White,
    onBackground = MainTextColor

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
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val locale = LocalConfiguration.current.locales[0]

    val regular = FontFamily(
        Font(
            resId = when (locale.language) {
                "en" -> R.font.poppins_regular
                else -> R.font.montserrat_regular
            },
            weight = FontWeight(400)
        )
    )

    val semibold = FontFamily(
        Font(
            resId = when (locale.language) {
                "en" -> R.font.poppins_semibold
                else -> R.font.montserrat_semibold
            }
        )
    )

    val typography = Typography(
        bodyLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = semibold,
            fontWeight = FontWeight(500),
            fontSize = 14.sp,
            lineHeight = 21.sp
        ),
        bodySmall = TextStyle(
            fontFamily = regular,
            fontWeight = FontWeight(400),
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        headlineLarge = TextStyle(
            fontFamily = semibold,
            fontWeight = FontWeight(600),
            fontSize = 32.sp,
            lineHeight = 48.sp
        ),
        headlineMedium = TextStyle(
            fontFamily = semibold,
            fontWeight = FontWeight(500),
            fontSize = 28.sp,
            lineHeight = 42.sp
        ),
        titleMedium = TextStyle(
            fontFamily = regular,
            fontWeight = FontWeight(400),
            fontSize = 14.sp,
        ),
        titleSmall = TextStyle(
            fontFamily = regular,
            fontWeight = FontWeight(400),
            fontSize = 12.sp,
            lineHeight = 21.sp
        )
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}