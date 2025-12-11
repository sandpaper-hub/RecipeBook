package com.example.recipebook.util

import com.example.recipebook.domain.model.ThemeMode

fun Int.convertToFollowersFormat(): String {
    return when {
        this >= 1_000_000 -> String.format("%.1fM", this / 1_000_000f)
        this >= 1_000 -> String.format("%.1fK", this / 1_000f)
        else -> this.toString()
    }.replace(".0", "")
}

fun String.convertToNickName(): String =
    trim().substringBefore("@")

fun String.toLocaleCode(): String = when(this) {
    "Русский", "Russian", "RU" -> "ru"
    "English", "Английский", "EN" -> "en"
    else -> this
}

fun ThemeMode.toTitleString(): String {
    return this.toString().lowercase().replaceFirstChar { it.uppercase() }
}