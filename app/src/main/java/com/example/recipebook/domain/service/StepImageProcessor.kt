package com.example.recipebook.domain.service

import com.example.recipebook.domain.model.recipe.step.EditStep
import com.example.recipebook.domain.model.ImageSourceType
import com.example.recipebook.domain.useCase.GetStepImageUrlUseCase
import com.example.recipebook.domain.useCase.recipe.DeleteStepImageUseCase
import javax.inject.Inject

class StepImageProcessor @Inject constructor(
    private val getStepImageUrlUseCase: GetStepImageUrlUseCase,
    private val deleteStepImageUseCase: DeleteStepImageUseCase
) {
    suspend fun resolveImageUrl(
        recipeId: String,
        step: EditStep,
        oldStep: EditStep?
    ): String? {
        return when (val sourceType = step.imageSourceType) {
            is ImageSourceType.None -> {
                if (oldStep?.imageSourceType is ImageSourceType.Remote) {
                    deleteStepImageUseCase.execute(recipeId = recipeId, stepId = oldStep.id)
                }
                null
            }

            is ImageSourceType.Remote -> sourceType.source
            is ImageSourceType.Local ->
                getStepImageUrlUseCase.execute(
                    recipeId = recipeId,
                    stepId = step.id,
                    source = sourceType.source
                )
        }
    }
}