package com.example.recipebook.domain.useCase.settings.changeTheme

import com.example.recipebook.domain.model.ThemeMode
import com.example.recipebook.domain.repository.DataStoreRepository
import javax.inject.Inject

class ChangeThemeUseCaseImpl @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
): ChangeThemeUseCase {
    override suspend fun execute(mode: ThemeMode) {
        dataStoreRepository.setTheme(mode)
    }
}