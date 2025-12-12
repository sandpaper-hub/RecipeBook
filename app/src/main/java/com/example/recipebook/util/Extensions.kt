package com.example.recipebook.util

fun Int.convertToFollowersFormat(): String {
    return when {
        this >= 1_000_000 -> String.format("%.1fM", this / 1_000_000f)
        this >= 1_000 -> String.format("%.1fK", this / 1_000f)
        else -> this.toString()
    }.replace(".0", "")
}

fun String.convertToNickName(): String =
    trim().substringBefore("@")

fun String.fromLocaleCode(): String = when (this) {
    "ru" -> "Русский"
    "en" -> "English"
    else -> this
}