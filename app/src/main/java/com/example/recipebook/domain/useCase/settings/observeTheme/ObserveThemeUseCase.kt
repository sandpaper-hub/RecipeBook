package com.example.recipebook.domain.useCase.settings.observeTheme

import com.example.recipebook.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface ObserveThemeUseCase {
    fun execute(): Flow<ThemeMode>
}