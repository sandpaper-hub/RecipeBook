package com.example.recipebook.presentation.viewModel.profileScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.profile.ProfileInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileInteractor: ProfileInteractor
) : ViewModel() {
    var uiState by mutableStateOf(ProfileState())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun loadProfile() {
        viewModelScope.launch {
            isLoading = true

            val result = profileInteractor.getUserProfile()

            result
                .onSuccess {
                    uiState = uiState.copy(
                        uid = it.uid,
                        fullName = it.fullName,
                        nickName = it.nickName,
                        image = it.photoUrl ?: ""

                    )
                    isLoading = false
                }
                .onFailure { error ->
                    errorMessage = error.message
                    isLoading = false
                }
        }
    }

    fun onNameChanged(newValue: String) {
        uiState = uiState.copy(fullName = newValue)
    }

    fun onNickNameChanged(newValue: String) {
        uiState = uiState.copy(nickName = newValue)
    }

    fun updateUserData() {
        viewModelScope.launch {
            profileInteractor.updateUserData(
                data = mapOf(
                    "fullName" to uiState.fullName,
                    "nickName" to uiState.nickName,
                    "photoUrl" to uiState.image

                )
            )
        }
    }
}