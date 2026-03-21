package com.example.recipebook.domain.useCase.settings.changeTheme

import com.example.recipebook.domain.model.ThemeMode

interface ChangeThemeUseCase {
    suspend fun execute(mode: ThemeMode)
}