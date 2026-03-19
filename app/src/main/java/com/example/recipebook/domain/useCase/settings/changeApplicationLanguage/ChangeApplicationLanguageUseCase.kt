package com.example.recipebook.domain.useCase.settings.changeApplicationLanguage

interface ChangeApplicationLanguageUseCase {
    suspend fun execute(value: String?)
}