package com.example.recipebook.domain.interactor.settings

import com.example.recipebook.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface SettingsInteractor {
    fun getSystemLanguage(): String?
    suspend fun changeApplicationLanguage(value: String)
    suspend fun observeSavedLanguage(): Flow<String?>
    suspend fun logOut()
    fun getTheme(): Flow<ThemeMode>
    suspend fun changeTheme(mode: ThemeMode)
}