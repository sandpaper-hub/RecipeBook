package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.repository.DataStoreRepository
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    fun execute() = dataStoreRepository.observeTheme()
}