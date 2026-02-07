package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipe
import com.example.recipebook.domain.model.recipe.createRecipe.NewRecipeStep
import com.example.recipebook.domain.repository.RecipesRepository
import javax.inject.Inject

class UploadNewRecipeUseCase @Inject constructor(
    private val recipesRepository: RecipesRepository
) {
    suspend fun execute(newRecipe: NewRecipe, steps: List<NewRecipeStep>) {
        recipesRepository.saveRecipe(newRecipe, steps)
    }
}