package com.example.recipebook.data.mapper

import com.example.recipebook.data.dto.createRecipe.NewStepDto
import com.example.recipebook.data.dto.getRecipe.StepDto
import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipeStep
import com.example.recipebook.domain.model.recipe.step.Step

fun NewRecipeStep.toDto(): NewStepDto {
    return NewStepDto(
        order = this.order,
        title = this.title,
        imageUrl = this.imageUrl,
        description = this.description
    )
}

fun StepDto.toDomain(): Step{
    return Step(
        title = this.title,
        description = this.description,
        imageSource = this.imageUrl
    )
}