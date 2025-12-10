package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.repository.SettingsRepository
import javax.inject.Inject

class ChangeApplicationLanguageUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend fun execute(value: String?) {
        settingsRepository.saveLanguageCode(value)
        settingsRepository.changeLanguage(value)
    }
}