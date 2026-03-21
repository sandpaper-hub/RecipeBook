package com.example.recipebook.domain.useCase.settings.observeTheme

import com.example.recipebook.domain.model.ThemeMode
import com.example.recipebook.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveThemeUseCaseImpl @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ObserveThemeUseCase{
    override fun execute(): Flow<ThemeMode> = dataStoreRepository.observeTheme()
}