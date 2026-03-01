package com.example.recipebook.domain.useCase.deleteImage

import com.example.recipebook.domain.repository.DeleteImageRepository
import javax.inject.Inject

class DeleteStepImageUseCase @Inject constructor(
    private val deleteImageRepository: DeleteImageRepository
) {
    suspend fun execute(recipeId: String, stepId: String) {
        deleteImageRepository.deleteStepImageByPath(recipeId = recipeId, stepId = stepId)
    }
}