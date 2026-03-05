package com.example.recipebook.domain.useCase.recipe.deleteRecipeImage

import com.example.recipebook.domain.repository.DeleteImageRepository
import javax.inject.Inject

class DeleteRecipeImageUseCase @Inject constructor(
    private val deleteImageRepository: DeleteImageRepository
) {
    suspend fun execute(recipeId: String){
        deleteImageRepository.deleteRecipeImageByPath(recipeId)
    }
}