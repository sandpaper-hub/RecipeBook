package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.repository.SettingsRepository
import javax.inject.Inject

class GetSystemLanguageUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    fun execute(): String? {
        return settingsRepository.getSystemLanguage()
    }
}