package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.model.NewRecipe
import com.example.recipebook.domain.repository.UploadRecipeRepository
import javax.inject.Inject

class UploadNewRecipeUseCase @Inject constructor(
    private val uploadRecipeRepository: UploadRecipeRepository
) {
    suspend fun execute(newRecipe: NewRecipe) {
        uploadRecipeRepository.saveRecipe(newRecipe)
    }
}