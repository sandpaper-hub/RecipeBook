package com.example.recipebook.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import com.example.recipebook.R
import java.util.Locale

val PoppinsFontFamily = FontFamily(
    Font(resId = R.font.poppins_regular, FontWeight.Normal),
    Font(resId = R.font.poppins_semibold, FontWeight.SemiBold),
    Font(resId = R.font.poppins_medium, FontWeight.Medium),
    Font(resId = R.font.poppins_bold, FontWeight.Bold)
)

val MontserratFontFamily = FontFamily(
    Font(resId = R.font.montserrat_semibold, FontWeight.SemiBold),
    Font(resId = R.font.montserrat_bold, FontWeight.Bold),
    Font(resId = R.font.montserrat_regular, FontWeight.Normal),
    Font(resId = R.font.montserrat_medium, FontWeight.Medium)
)

@Composable
fun rememberAppFontFamily(locale: Locale): FontFamily {
    return when (locale.language) {
        "en" -> PoppinsFontFamily
        else -> MontserratFontFamily
    }
}

@Composable
fun rememberAppTypography(locale: Locale): Typography {
    val fontFamily = rememberAppFontFamily(locale)

    return Typography(
        //heading 1
        displaySmall = TextStyle(
            fontSize = 36.sp,
            fontFamily = fontFamily,
            lineHeight = 54.sp
        ),
        //heading 2
        headlineLarge = TextStyle(
            fontSize = 32.sp,
            fontFamily = fontFamily,
            lineHeight = 48.sp
        ),
        //heading 3
        headlineMedium = TextStyle(
            fontSize = 28.sp,
            fontFamily = fontFamily,
            lineHeight = 42.sp
        ),
        //heading 4
        headlineSmall = TextStyle(
            fontSize = 24.sp,
            fontFamily = fontFamily,
            lineHeight = 36.sp
        ),
        //heading 5
        titleLarge = TextStyle(
            fontSize = 20.sp,
            fontFamily = fontFamily,
            lineHeight = 30.sp
        ),
        //body 1
        bodyLarge = TextStyle(
            fontSize = 16.sp,
            fontFamily = fontFamily,
            lineHeight = 24.sp
        ),
        //body 2
        bodyMedium = TextStyle(
            fontSize = 14.sp,
            fontFamily = fontFamily,
            lineHeight = 21.sp
        ),
        //body 3
        labelMedium = TextStyle(
            fontSize = 12.sp,
            fontFamily = fontFamily,
        )
    )
}