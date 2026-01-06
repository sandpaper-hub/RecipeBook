package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.repository.UploadRecipeRepository
import javax.inject.Inject

class GetRecipeCoverUrlUseCase @Inject constructor(
    private val uploadRecipeRepository: UploadRecipeRepository
) {
    suspend fun execute(recipeId: String, imageSource: String?): String? =
        if (imageSource == null) null
        else uploadRecipeRepository.uploadRecipeImage(recipeId, imageSource)
}