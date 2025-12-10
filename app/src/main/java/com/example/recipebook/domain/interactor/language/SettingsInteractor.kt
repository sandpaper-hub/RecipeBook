package com.example.recipebook.domain.interactor.language

import kotlinx.coroutines.flow.Flow

interface SettingsInteractor {
    fun getSystemLanguage(): String?
    suspend fun changeApplicationLanguage(value: String)
    suspend fun observeSavedLanguage(): Flow<String?>
    suspend fun logOut()
}