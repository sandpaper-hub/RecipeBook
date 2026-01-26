package com.example.recipebook.presentation.viewModel.createRecipeScreen

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.recipes.RecipesInteractor
import com.example.recipebook.domain.model.recipe.RecipeIngredient
import com.example.recipebook.domain.model.recipe.RecipeStepDraft
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.IngredientUiState
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.NewRecipeUiState
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.RecipeStepUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateRecipeViewModel @Inject constructor(
    private val recipesInteractor: RecipesInteractor
) : ViewModel() {
    var uiState by mutableStateOf(NewRecipeUiState())
        private set

    init {
        uiState = uiState.copy(
            ingredients = listOf(
                IngredientUiState(
                    id = UUID.randomUUID().toString(),
                    ingredientValue = "",
                )
            ),
            recipeSteps = listOf(
                RecipeStepUiState(
                    id = UUID.randomUUID().toString(),
                    imageUri = null,
                    stepDescription = ""
                )
            )
        )
    }

    fun onRecipeImagePicked(uri: Uri?) {
        uiState = uiState.copy(recipeImageUri = uri)
    }

    fun onRecipeNameChanged(value: String) {
        uiState = uiState.copy(recipeName = value)
    }

    fun onRecipeDescriptionChanged(value: String) {
        uiState = uiState.copy(recipeDescription = value)
    }

    fun onRecipeTimeEstimationChanged(value: String) {
        uiState = uiState.copy(timeEstimation = value)
    }

    fun onIngredientChange(id: String, value: String) {
        uiState = uiState.copy(
            ingredients = uiState.ingredients.map {
                if (it.id == id) it.copy(ingredientValue = value) else it
            },
            editingIngredientId = null
        )
    }

    fun showCategoryMenu(isShow: Boolean) {
        uiState = uiState.copy(isCategoryMenuExpand = isShow)
    }

    fun onCategoryChange(value: String) {
        uiState = uiState.copy(
            recipeCategory = value,
            isCategoryMenuExpand = false
        )
    }

    fun removeIngredient(id: String) {
        uiState = uiState.copy(
            ingredients = uiState.ingredients.filterNot { it.id == id }
        )
    }

    fun addIngredient() {
        uiState = uiState.copy(
            ingredients = uiState.ingredients + IngredientUiState(
                id = UUID.randomUUID().toString(),
                ingredientValue = ""
            )
        )
    }

    fun showIngredientDialog(id: String?) {
        uiState = uiState.copy(
            editingIngredientId = id
        )
    }

    fun addStep() {
        uiState = uiState.copy(
            recipeSteps = uiState.recipeSteps + RecipeStepUiState(
                id = UUID.randomUUID().toString(),
                imageUri = null,
                stepDescription = ""
            )
        )
    }

    fun removeStep(id: String) {
        uiState = uiState.copy(
            recipeSteps = uiState.recipeSteps.filterNot { it.id == id }
        )
    }

    fun onStepDescriptionChange(id: String, value: String) {
        uiState = uiState.copy(
            recipeSteps = uiState.recipeSteps.map {
                if (it.id == id) it.copy(stepDescription = value) else it
            }
        )
    }

    fun onStepImageChange(id: String, uri: Uri?) {
        uiState = uiState.copy(
            recipeSteps = uiState.recipeSteps.map {
                if (it.id == id) it.copy(imageUri = uri) else it
            })
    }

    fun uploadNewRecipe() {
        viewModelScope.launch {
            runCatching {
                recipesInteractor.uploadNewRecipe(
                    recipeId = UUID.randomUUID().toString(),
                    recipeName = uiState.recipeName,
                    recipeDescription = uiState.recipeDescription,
                    recipeTimeEstimation = uiState.timeEstimation,
                    recipeImageSource = uiState.recipeImageUri?.toString(),
                    category = uiState.recipeCategory,
                    ingredients = uiState.ingredients.map { ingredient ->
                        RecipeIngredient(
                            id = ingredient.id,
                            value = ingredient.ingredientValue
                        )
                    },
                    steps = uiState.recipeSteps.map { recipeStepUiState ->
                        RecipeStepDraft(
                            id = recipeStepUiState.id,
                            imageSource = recipeStepUiState.imageUri?.toString(),
                            description = recipeStepUiState.stepDescription
                        )
                    }
                )
            }.onSuccess {
                //TODO
            }.onFailure { error ->
                uiState = uiState.copy(errorMessage = error.message)
            }

        }
    }
}