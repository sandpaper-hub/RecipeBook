package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.model.ThemeMode
import com.example.recipebook.domain.repository.DataStoreRepository
import javax.inject.Inject

class ChangeThemeUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend fun execute(mode: ThemeMode) {
        dataStoreRepository.setTheme(mode)
    }
}