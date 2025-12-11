package com.example.recipebook.domain.repository

import com.example.recipebook.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun getSavedLanguageFlow(): Flow<String?>
    suspend fun saveLanguageCode(code: String?)
    suspend fun clearUserData()
    fun observeTheme(): Flow<ThemeMode>
    suspend fun setTheme(theme: ThemeMode)
}