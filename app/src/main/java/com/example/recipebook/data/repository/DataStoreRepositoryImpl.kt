package com.example.recipebook.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.recipebook.domain.model.ThemeMode
import com.example.recipebook.domain.repository.DataStoreRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {

    private val languageKey = stringPreferencesKey("app_language_code")
    private val themeKey = stringPreferencesKey("app_theme")

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

    override fun observeTheme(): Flow<ThemeMode> = dataStore.data
        .map { preferences ->
            val value = preferences[themeKey] ?: ThemeMode.SYSTEM.name
            ThemeMode.valueOf(value)
        }

    override suspend fun setTheme(theme: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[themeKey] = theme.name
        }
    }
}