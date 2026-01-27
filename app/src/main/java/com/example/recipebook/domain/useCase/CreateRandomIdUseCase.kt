package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.repository.RecipesRepository
import javax.inject.Inject

class CreateRandomIdUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend fun execute(): String {
        return recipesRepository.createRandomId()
    }
}