package com.example.recipebook.data.repository

import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.recipebook.domain.repository.SettingsRepository
import jakarta.inject.Inject

class SettingsRepositoryImpl @Inject constructor() : SettingsRepository {

    override fun getSystemLanguage(): String?{
        val systemLocale = LocaleListCompat.getDefault()[0]
        return systemLocale?.language
    }

    override fun changeLanguage(value: String) {
        val locale = LocaleListCompat.forLanguageTags(value)
        Handler(Looper.getMainLooper()).post {
            AppCompatDelegate.setApplicationLocales(locale)
        }
    }
}