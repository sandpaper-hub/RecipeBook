package com.example.recipebook.domain.model.recipe.update

import com.example.recipebook.domain.model.recipe.step.EditStep
data class RecipeDifference(
    val stepsToDelete: List<EditStep>,
    val stepsToAdd: List<EditStep>,
    val stepsToUpdate: List<EditStep>
)
