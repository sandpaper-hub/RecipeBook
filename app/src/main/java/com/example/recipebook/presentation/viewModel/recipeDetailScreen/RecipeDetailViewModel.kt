package com.example.recipebook.presentation.viewModel.recipeDetailScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.recipes.deleteRecipeInteractor.DeleteRecipeInteractor
import com.example.recipebook.domain.useCase.recipe.addRecipeToCollectionUseCase.AddRecipeToCollectionUseCase
import com.example.recipebook.domain.useCase.collection.observeUserCollectionUseCase.ObserveUserCollectionUseCase
import com.example.recipebook.domain.useCase.recipe.removeRecipeFromCollectionUseCase.RemoveBrokenIdUseCase
import com.example.recipebook.domain.useCase.userProfile.getUserIdFlow.GetUserIdFlowUseCase
import com.example.recipebook.domain.useCase.recipe.observeRecipeById.ObserveRecipeByIdUseCase
import com.example.recipebook.navigation.mainHomeGraph.recipeDetailGraph.RecipeDetailDestination
import com.example.recipebook.presentation.viewModel.model.TimeEstimationUiState
import com.example.recipebook.presentation.viewModel.recipeDetailScreen.model.CollectionUiState
import com.example.recipebook.presentation.viewModel.recipeDetailScreen.model.IngredientUiState
import com.example.recipebook.presentation.viewModel.recipeDetailScreen.model.RecipeDetailEvent
import com.example.recipebook.presentation.viewModel.recipeDetailScreen.model.RecipeDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val observeRecipeByIdUseCaseImpl: ObserveRecipeByIdUseCase,
    private val getUserIdFlowUseCase: GetUserIdFlowUseCase,
    private val observeUserCollectionUseCase: ObserveUserCollectionUseCase,
    private val addRecipeToCollectionUseCase: AddRecipeToCollectionUseCase,
    private val removeBrokenIdUseCase: RemoveBrokenIdUseCase,
    private val deleteRecipeInteractor: DeleteRecipeInteractor,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val recipeId: String =
        checkNotNull(savedStateHandle[RecipeDetailDestination.RECIPE_ID_ARG]).toString()
    private val _events = MutableSharedFlow<RecipeDetailEvent>()
    val events = _events.asSharedFlow()
    private val _uiState = MutableStateFlow(RecipeDetailUiState())
    val uiState: StateFlow<RecipeDetailUiState> = _uiState

    init {
        observeUserCollections()
        getRecipeById()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeUserCollections() {
        getUserIdFlowUseCase.execute()
            .flatMapLatest { uid ->
                if (uid == null) {
                    flowOf(emptyList())
                } else {
                    observeUserCollectionUseCase.execute(uid)
                }
            }
            .onStart {
                _uiState.update {
                    it.copy(
                        isCollectionsLoading = true
                    )
                }
            }
            .onEach { collections ->
                _uiState.update { currentState ->
                    val updated = collections.map { collection ->
                        val oldItem = currentState.collectionsUiState
                            .find { it.id == collection.id }
                        CollectionUiState(
                            id = collection.id,
                            name = collection.name,
                            imageUrl = collection.imageSource,
                            containRecipe = collection.recipeIds.contains(recipeId),
                            isUpdating = oldItem?.isUpdating ?: false
                        )
                    }
                    currentState.copy(collectionsUiState = updated)
                }
            }
            .catch { error ->
                _uiState.update {
                    it.copy(collectionsErrorMessage = error.message)
                }
            }
            .launchIn(viewModelScope)
    }

    fun toggleRecipeInCollection(collectionId: String) {
        val currentState = _uiState.value

        val target = currentState.collectionsUiState
            .find { it.id == collectionId }
            ?: return

        if (target.isUpdating) {
            return
        }

        val wasContained = target.containRecipe

        _uiState.update { current ->
            current.copy(
                collectionsUiState = current.collectionsUiState.map { item ->
                    if (item.id == collectionId) {
                        item.copy(
                            containRecipe = !item.containRecipe,
                            isUpdating = true
                        )
                    } else item
                }
            )
        }

        syncToggle(collectionId, wasContained)
    }

    private fun syncToggle(collectionId: String, containedRecipe: Boolean) {
        viewModelScope.launch {
            runCatching {
                if (containedRecipe) {
                    removeBrokenIdUseCase.execute(
                        collectionId = collectionId,
                        recipeId = recipeId
                    )
                } else {
                    addRecipeToCollectionUseCase.execute(
                        collectionId = collectionId,
                        recipeId = recipeId
                    )
                }
            }
                .onFailure {
                    rollbackCollectionUiState(collectionId)
                }

            _uiState.update { state ->
                state.copy(
                    collectionsUiState = state.collectionsUiState.map { item ->
                        if (item.id == collectionId) {
                            item.copy(isUpdating = false)
                        } else item
                    }
                )
            }
        }
    }

    private fun rollbackCollectionUiState(collectionId: String) {
        _uiState.update { state ->
            state.copy(
                collectionsUiState = state.collectionsUiState.map { collectionUiState ->
                    if (collectionUiState.id == collectionId) {
                        collectionUiState.copy(
                            containRecipe = !collectionUiState.containRecipe
                        )
                    } else collectionUiState
                }
            )
        }
    }

    private fun getRecipeById() {
        viewModelScope.launch {
            observeRecipeByIdUseCaseImpl.execute(recipeId)
                .onStart {
                    _uiState.update {
                        it.copy(
                            isRecipeLoading = true
                        )
                    }
                }
                .onEach { recipe ->
                    _uiState.update {
                        it.copy(
                            imageUrl = recipe.imageUrl,
                            name = recipe.recipeName,
                            description = recipe.recipeDescription,
                            category = recipe.category,
                            timeEstimationUiState = TimeEstimationUiState(
                                hour = recipe.recipeTimeEstimation.hour,
                                minute = recipe.recipeTimeEstimation.minute
                            ),
                            ingredients = recipe.ingredients.map { ingredient ->
                                IngredientUiState(
                                    id = ingredient.id,
                                    value = ingredient.value,
                                    amount = ingredient.amount,
                                    measure = ingredient.measure
                                )
                            },
                            createdAt = recipe.createdAt
                        )
                    }
                }
                .catch { error ->
                    _uiState.update {
                        it.copy(
                            recipeErrorMessage = error.message
                        )
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    fun openDeleteDialog(isOpen: Boolean) {
        _uiState.update {
            it.copy(isOpenedDeleteDialog = isOpen)
        }
    }

    fun deleteRecipe() {
        viewModelScope.launch {
            runCatching {
                val collectionIdsToDelete =
                    _uiState.value.collectionsUiState.filter { it.containRecipe }.map {
                        it.id
                    }

                deleteRecipeInteractor.invoke(recipeId, collectionIdsToDelete)
            }
                .onSuccess {
                    _uiState.update {
                        it.copy(isOpenedDeleteDialog = false)
                    }
                    _events.emit(RecipeDetailEvent.GoBack)
                }
        }
    }

    fun showCollectionSheet(isShow: Boolean) {
        _uiState.update {
            it.copy(
                isShowCollectionSheet = isShow
            )
        }
    }

    fun goBack() {
        viewModelScope.launch {
            _events.emit(RecipeDetailEvent.GoBack)
        }
    }

    fun onRecipeEdit() {
        viewModelScope.launch {
            _events.emit(RecipeDetailEvent.OnRecipeEditScreen(recipeId))
        }
    }

    fun onCookingScreen() {
        viewModelScope.launch {
            _events.emit(RecipeDetailEvent.OnCookingScreen(recipeId))
        }
    }

    fun showDropdownMenu(isOpen: Boolean) {
        _uiState.update {
            it.copy(isOpenDropdownMenu = isOpen)
        }
    }
}