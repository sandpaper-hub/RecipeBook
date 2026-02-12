package com.example.recipebook.presentation.viewModel.recipeDetailScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.R
import com.example.recipebook.domain.interactor.collection.CollectionInteractor
import com.example.recipebook.domain.interactor.recipes.RecipesInteractor
import com.example.recipebook.domain.model.recipe.getRecipe.IngredientMeasure
import com.example.recipebook.navigation.mainHomeGraph.recipeDetailGraph.RecipeDetailRoutes
import com.example.recipebook.presentation.ui.commonUi.dropDownMenu.model.DropdownMenuItem
import com.example.recipebook.presentation.viewModel.recipeDetailScreen.model.CollectionUiState
import com.example.recipebook.presentation.viewModel.recipeDetailScreen.model.DropdownMenuAction
import com.example.recipebook.presentation.viewModel.recipeDetailScreen.model.IngredientUiState
import com.example.recipebook.presentation.viewModel.recipeDetailScreen.model.RecipeDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val recipesInteractor: RecipesInteractor,
    private val collectionsInteractor: CollectionInteractor,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecipeDetailUiState())
    val uiState: StateFlow<RecipeDetailUiState> = _uiState
    private var recipeId = ""

    init {
        initDropdownMenuItems()
        recipeId =
            checkNotNull(savedStateHandle[RecipeDetailRoutes.RecipeDetail.RECIPE_ID_ARG]).toString()
        observeUserCollections()
        _uiState.update {
            it.copy(
                id = recipeId
            )
        }
        getRecipeById(recipeId)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeUserCollections() {
        collectionsInteractor.getUserIdFlow()
            .flatMapLatest { uid ->
                if (uid == null) {
                    flowOf(emptyList())
                } else {
                    collectionsInteractor.observeUserCollections(uid)
                }
            }
            .onEach { collections ->
                val collectionsUiState = collections.map { collection ->
                    CollectionUiState(
                        id = collection.id,
                        name = collection.name,
                        imageUrl = collection.imageUrl,
                        containRecipe = collection.recipeIds.contains(recipeId)
                    )
                }

                _uiState.update {
                    it.copy(collectionsUiState = collectionsUiState)
                }
            }
            .launchIn(viewModelScope)
    }

    fun addRecipeToCollection(collectionId: String) {

    }

    fun removeRecipeFromCollection(collectionId: String) {

    }

    private fun getRecipeById(recipeId: String) {
        viewModelScope.launch {
            val recipe = recipesInteractor.getRecipeById(recipeId)
            _uiState.update {
                it.copy(
                    imageUrl = recipe.imageUrl,
                    name = recipe.recipeName,
                    description = recipe.recipeDescription,
                    category = recipe.category,
                    timeEstimation = recipe.recipeTimeEstimation,
                    ingredients = recipe.ingredients.map { recipe ->
                        IngredientUiState(
                            id = recipe.id,
                            value = recipe.value,
                            amount = recipe.amount,
                            measure = when (recipe.measure) {
                                IngredientMeasure.TEASPOON -> R.string.measure_teaspoon
                                IngredientMeasure.TABLESPOON -> R.string.measure_tablespoon
                                IngredientMeasure.MILLILITER -> R.string.measure_ml
                                IngredientMeasure.LITER -> R.string.measure_l
                                IngredientMeasure.GRAM -> R.string.measure_g
                                IngredientMeasure.KILOGRAM -> R.string.measure_kg
                                IngredientMeasure.PCS -> R.string.measure_pcs
                                else -> R.string.unknown_measure
                            }
                        )
                    },
                    createdAt = recipe.createdAt
                )
            }
        }
    }

    fun openDeleteDialog(isOpen: Boolean) {
        _uiState.update {
            it.copy(isOpedDeleteDialog = isOpen)
        }
    }

    fun deleteRecipe(recipeId: String, onBack: () -> Unit) {
        viewModelScope.launch {
            runCatching {
                recipesInteractor.deleteRecipe(recipeId)
            }
                .onSuccess {
                    onBack()
                }
        }
    }

    fun showSheet(isShow: Boolean) {
        _uiState.update {
            it.copy(
                isShowCollectionSheet = isShow
            )
        }
    }

    private fun initDropdownMenuItems() {
        val dropdownMenuItems = listOf(
            DropdownMenuItem(
                action = DropdownMenuAction.EDIT,
                titleResource = R.string.edit_text
            ),
            DropdownMenuItem(
                action = DropdownMenuAction.DELETE,
                titleResource = R.string.delete_text
            )
        )

        _uiState.update {
            it.copy(
                dropdownMenuItems = dropdownMenuItems
            )
        }
    }

    fun isOpenDropdownMenu(isOpen: Boolean) {
        _uiState.update {
            it.copy(isOpenDropdownMenu = isOpen)
        }
    }
}