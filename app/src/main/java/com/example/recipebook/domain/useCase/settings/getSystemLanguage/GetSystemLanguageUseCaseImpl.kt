package com.example.recipebook.domain.useCase.settings.getSystemLanguage

import com.example.recipebook.domain.repository.SettingsRepository
import javax.inject.Inject

class GetSystemLanguageUseCaseImpl @Inject constructor(
    private val settingsRepository: SettingsRepository
) : GetSystemLanguageUseCase{
    override fun execute(): String? {
        return settingsRepository.getSystemLanguage()
    }
}