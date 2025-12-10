package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.repository.DataStoreRepository
import javax.inject.Inject

class GetSystemLanguageUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    fun execute(): String? {
        return dataStoreRepository.getSystemLanguage()
    }
}