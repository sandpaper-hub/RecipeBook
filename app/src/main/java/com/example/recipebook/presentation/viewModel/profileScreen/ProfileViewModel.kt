package com.example.recipebook.presentation.viewModel.profileScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.profile.ProfileInteractor
import com.example.recipebook.presentation.viewModel.profileScreen.model.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileInteractor: ProfileInteractor
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState


    init {
        observeUserProfile()
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