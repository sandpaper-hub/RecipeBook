package com.example.recipebook.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.recipebook.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = PoppinsSemiBold,
        fontWeight = FontWeight(500),
        fontSize = 14.sp,
        lineHeight = 21.sp
    ),
    bodySmall = TextStyle(
        fontFamily = PoppinsRegular,
        fontWeight = FontWeight(400),
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = PoppinsSemiBold,
        fontWeight = FontWeight(600),
        fontSize = 32.sp,
        lineHeight = 48.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = PoppinsSemiBold,
        fontWeight = FontWeight(500),
        fontSize = 28.sp,
        lineHeight = 42.sp
    ),
    titleMedium = TextStyle(
        fontFamily = PoppinsRegular,
        fontWeight = FontWeight(400),
        fontSize = 12.sp,
        color = TitleGray
    ),
    titleSmall = TextStyle(
        fontFamily = PoppinsRegular,
        fontWeight = FontWeight(400),
        fontSize = 14.sp,
        lineHeight = 21.sp,
        color = TitleGray
    )
)