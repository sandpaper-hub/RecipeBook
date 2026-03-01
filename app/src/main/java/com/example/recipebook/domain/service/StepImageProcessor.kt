package com.example.recipebook.domain.service

import com.example.recipebook.domain.model.recipe.step.EditStep
import com.example.recipebook.domain.model.recipe.step.SourceType
import com.example.recipebook.domain.useCase.GetStepImageUrlUseCase
import com.example.recipebook.domain.useCase.deleteImage.DeleteStepImageUseCase
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
        return when (val sourceType = step.sourceType) {
            is SourceType.None -> {
                if (oldStep?.sourceType is SourceType.Remote) {
                    deleteStepImageUseCase.execute(recipeId = recipeId, stepId = oldStep.id)
                }
                null
            }

            is SourceType.Remote -> sourceType.source
            is SourceType.Local ->
                getStepImageUrlUseCase.execute(
                    recipeId = recipeId,
                    stepId = step.id,
                    source = sourceType.source
                )
        }
    }
}