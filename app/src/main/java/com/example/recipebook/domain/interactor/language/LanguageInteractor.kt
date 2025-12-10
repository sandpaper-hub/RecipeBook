package com.example.recipebook.domain.interactor.language

import kotlinx.coroutines.flow.Flow

interface LanguageInteractor {
    fun getSystemLanguage(): String?
    suspend fun changeApplicationLanguage(value: String)
    suspend fun observeSavedLanguage(): Flow<String?>
}