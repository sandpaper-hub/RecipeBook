package com.example.recipebook.domain.useCase.settings.observeSavedLanguage

import kotlinx.coroutines.flow.Flow

interface ObserveSavedLanguageUseCase {
    fun execute(): Flow<String?>
}