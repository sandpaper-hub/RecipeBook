package com.example.recipebook.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getSystemLanguage(): String?
    fun getSavedLanguageFlow(): Flow<String?>
    suspend fun saveLanguageCode(code: String?)
    fun changeLanguage(value: String?)
}