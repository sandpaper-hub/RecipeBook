package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.repository.DataStoreRepository
import com.example.recipebook.domain.repository.SettingsRepository
import javax.inject.Inject

class ChangeApplicationLanguageUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend fun execute(value: String?) {
        dataStoreRepository.saveLanguageCode(value)
        settingsRepository.changeLanguage(value)
    }
}