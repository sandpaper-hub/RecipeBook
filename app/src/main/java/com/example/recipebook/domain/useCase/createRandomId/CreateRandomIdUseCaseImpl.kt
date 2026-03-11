package com.example.recipebook.domain.useCase.createRandomId

import com.example.recipebook.domain.repository.RecipesRepository
import javax.inject.Inject

class CreateRandomIdUseCaseImpl @Inject constructor(
    private val recipesRepository: RecipesRepository
): CreateRandomIdUseCase {
    override suspend fun execute(): String {
        return recipesRepository.createRandomId()
    }
}