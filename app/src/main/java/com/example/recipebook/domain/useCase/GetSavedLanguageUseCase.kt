package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetSavedLanguageUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    fun observeSavedLanguage(): Flow<String?> = settingsRepository.getSavedLanguageFlow()
    suspend fun getOnce(): String? = settingsRepository.getSavedLanguageFlow().first()
}