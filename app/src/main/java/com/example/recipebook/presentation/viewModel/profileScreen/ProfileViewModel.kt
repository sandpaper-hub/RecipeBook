package com.example.recipebook.presentation.viewModel.profileScreen

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.profile.ProfileInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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

    val allowedRegex = Regex("^[A-Za-z0-9._]*$")

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
                        remoteImageUrl = it.photoUrl ?: ""

                    )
                    isLoading = false
                }
                .onFailure { error ->
                    uiState = uiState.copy(errorMessage = error.message)
                    isLoading = false
                }
        }
    }

    fun onNameChanged(newValue: String) {
        uiState = uiState.copy(fullName = newValue)
    }

    fun onNickNameChanged(newValue: String) {
        uiState = if (allowedRegex.matches(newValue)) {
            uiState.copy(nickName = newValue)
        } else {
            uiState.copy(errorMessage = "No specific symbols")
        }
    }

    fun updateUserData(onBackNavigation: () -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(isSaving = true)

            if (uiState.fullName.isBlank() || uiState.nickName.isBlank()) {
                uiState = uiState.copy(
                    errorMessage = "Full Name or NickName shouldn't be blank",
                    isSaving = false
                )
                return@launch
            }

            val result = profileInteractor.updateUserData(
                data = mapOf(
                    "fullName" to uiState.fullName,
                    "nickName" to uiState.nickName
                ),
                uriString = if (uiState.localImageUri == null) null else uiState.localImageUri.toString()
            )
            delay(2000)
            uiState = if (result.isSuccess) {
                uiState.copy(isSaving = false, localImageUri = null)
            } else {
                uiState.copy(isSaving = false, errorMessage = result.exceptionOrNull()?.message)
            }
            onBackNavigation()
        }
    }

    fun onImagePicked(uri: Uri?) {
        uiState = uiState.copy(localImageUri = uri)
    }

    fun refreshImageUri() {
        viewModelScope.launch {
            delay(2000L)
            uiState = uiState.copy(localImageUri = null)
        }
    }
}