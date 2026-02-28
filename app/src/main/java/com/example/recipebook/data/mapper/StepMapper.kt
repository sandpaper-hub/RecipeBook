package com.example.recipebook.data.mapper

import com.example.recipebook.data.dto.createRecipe.NewStepDto
import com.example.recipebook.data.dto.getRecipe.StepDto
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipeStep
import com.example.recipebook.domain.model.recipe.step.Step

fun UploadRecipeStep.toDto(): NewStepDto {
    return NewStepDto(
        id = this.id,
        order = this.order,
        title = this.title,
        imageUrl = this.imageUrl,
        description = this.description
    )
}

fun StepDto.toDomain(): Step{
    return Step(
        id = this.id,
        title = this.title,
        order = this.order,
        description = this.description,
        imageSource = this.imageUrl
    )
}