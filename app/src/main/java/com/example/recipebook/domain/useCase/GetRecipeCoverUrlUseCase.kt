package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.repository.UploadRecipeRepository
import javax.inject.Inject

class GetRecipeCoverUrlUseCase @Inject constructor(
    private val uploadRecipeRepository: UploadRecipeRepository
) {
    suspend fun execute(recipeId: String, imageSource: String?): String =
        uploadRecipeRepository.uploadRecipeImage(recipeId, imageSource)
}