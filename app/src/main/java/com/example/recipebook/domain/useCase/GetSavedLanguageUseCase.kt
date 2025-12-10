package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetSavedLanguageUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    fun observeSavedLanguage(): Flow<String?> = dataStoreRepository.getSavedLanguageFlow()
    suspend fun getOnce(): String? = dataStoreRepository.getSavedLanguageFlow().first()
}