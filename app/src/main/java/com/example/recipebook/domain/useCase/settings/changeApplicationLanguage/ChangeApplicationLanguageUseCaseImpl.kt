package com.example.recipebook.domain.useCase.settings.changeApplicationLanguage

import com.example.recipebook.domain.repository.DataStoreRepository
import com.example.recipebook.domain.repository.SettingsRepository
import javax.inject.Inject

class ChangeApplicationLanguageUseCaseImpl @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val settingsRepository: SettingsRepository
) : ChangeApplicationLanguageUseCase{
    override suspend fun execute(value: String?) {
        dataStoreRepository.saveLanguageCode(value)
        settingsRepository.changeLanguage(value)
    }
}