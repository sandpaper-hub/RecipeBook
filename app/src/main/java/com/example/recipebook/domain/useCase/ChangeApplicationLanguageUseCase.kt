package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.repository.DataStoreRepository
import javax.inject.Inject

class ChangeApplicationLanguageUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend fun execute(value: String?) {
        dataStoreRepository.saveLanguageCode(value)
        dataStoreRepository.changeLanguage(value)
    }
}