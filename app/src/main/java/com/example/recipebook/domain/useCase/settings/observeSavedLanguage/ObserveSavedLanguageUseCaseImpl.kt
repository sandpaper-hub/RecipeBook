package com.example.recipebook.domain.useCase.settings.observeSavedLanguage

import com.example.recipebook.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveSavedLanguageUseCaseImpl @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
): ObserveSavedLanguageUseCase {
    override fun execute(): Flow<String?> = dataStoreRepository.getSavedLanguageFlow()
}