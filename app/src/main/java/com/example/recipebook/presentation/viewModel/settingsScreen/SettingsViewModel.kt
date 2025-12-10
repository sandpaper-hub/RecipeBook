package com.example.recipebook.presentation.viewModel.settingsScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.profile.ProfileInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val profileInteractor: ProfileInteractor
) : ViewModel() {
    var uiState by mutableStateOf(SettingsUiState())
        private set

    init {
        observeUserProfile()
    }

    private fun observeUserProfile() {
        viewModelScope.launch {
            profileInteractor.observerUserProfile()
                .catch { error ->
                    uiState = uiState.copy(errorMessage = error.message)
                }
                .collect { userProfile ->
                    uiState = uiState.copy(
                        uid = userProfile.uid,
                        fullName = userProfile.fullName,
                        nickName = userProfile.nickName,
                        imageUrl = userProfile.photoUrl
                    )
                }
        }
    }
}