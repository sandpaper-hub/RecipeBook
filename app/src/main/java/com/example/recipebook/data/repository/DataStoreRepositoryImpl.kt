package com.example.recipebook.data.repository

import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.recipebook.domain.repository.DataStoreRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {

    private val languageKey = stringPreferencesKey("app_language_code")

    override fun getSavedLanguageFlow(): Flow<String?> {
        return dataStore.data
            .map { preferences -> preferences[languageKey] }
    }

    override suspend fun saveLanguageCode(code: String?) {
        if (code != null) {
            dataStore.edit { preferences ->
                preferences[languageKey] = code
            }
        }
    }

    override fun getSystemLanguage(): String? {
        val systemLocale = LocaleListCompat.getDefault()[0]
        return systemLocale?.language
    }

    override fun changeLanguage(value: String?) {
        val locale = LocaleListCompat.forLanguageTags(value)
        Handler(Looper.getMainLooper()).post {
            AppCompatDelegate.setApplicationLocales(locale)
        }
    }

    override suspend fun clearUserData() {
        dataStore.edit { preferences ->
            val localeValue = preferences[languageKey]
            preferences.asMap().keys.forEach { key ->
                preferences.remove(key)
            }

            if (localeValue != null) {
                preferences[languageKey] = localeValue
            }
        }
    }
}