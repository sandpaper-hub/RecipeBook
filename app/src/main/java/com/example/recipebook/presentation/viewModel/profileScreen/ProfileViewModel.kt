package com.example.recipebook.presentation.viewModel.profileScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.profile.ProfileInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileInteractor: ProfileInteractor
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileState())
    val uiState: StateFlow<ProfileState> = _uiState


    init {
        observeUserProfile()
        observeUserRecipes()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeUserRecipes() {
        profileInteractor.getUserIdFlow()
            .flatMapLatest { uid ->
                if (uid == null) {
                    flowOf(emptyList())
                } else {
                    profileInteractor.observeUserRecipes(uid)
                }
            }
            .onStart {
                _uiState.update {
                    it.copy(isRecipesLoading = true)
                }
            }
            .onEach { recipes ->
                _uiState.update {
                    it.copy(
                        recipes = recipes,
                        isRecipesLoading = false
                    )
                }
            }
            .catch { throwable ->
                _uiState.update {
                    it.copy(
                        isRecipesLoading = false,
                        errorMessage = throwable.message
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeUserProfile() {
        profileInteractor.observerUserProfile()
            .onEach { userProfile ->
                _uiState.update {
                    it.copy(
                        fullName = userProfile.fullName,
                        nickName = userProfile.nickName,
                        profileImageUrl = userProfile.photoUrl,
                        errorMessage = null
                    )
                }
            }
            .catch { error ->
                _uiState.update {
                    it.copy(
                        errorMessage = error.message
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}