package com.example.recipebook.presentation.viewModel.editProfileScreen

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.profile.ProfileInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val profileInteractor: ProfileInteractor
) : ViewModel() {
    var uiState by mutableStateOf(EditProfileState())
        private set

    init {
        observeUserProfile()
    }

    val allowedRegex = Regex("^[A-Za-z0-9._]*$")

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
            uiState = uiState.copy(
                isSaving = true
            )

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
                uriString = uiState.localImageSource.toString()
            )
            delay(2000)
            uiState = if (result.isSuccess) {
                uiState.copy(
                    isSaving = false,
                    localImageSource = null
                )
            } else {
                uiState.copy(
                    isSaving = false,
                    errorMessage = result.exceptionOrNull()?.message
                )
            }
            if (result.isSuccess) {
                onBackNavigation()
            }
        }
    }

    fun onImagePicked(uri: Uri?) {
        uiState = uiState.copy(
            localImageSource = uri
        )
    }

    fun refreshImageUri() {
        viewModelScope.launch {
            delay(2000L)
            uiState = uiState.copy(
                localImageSource = null
            )
        }
    }

    private fun observeUserProfile() {
        viewModelScope.launch {
            profileInteractor.observerUserProfile()
                .catch { error ->
                    uiState = uiState.copy(
                        errorMessage = error.message
                    )
                }
                .collect { userProfile ->
                    uiState = uiState.copy(
                        fullName = userProfile.fullName,
                        nickName = userProfile.nickName,
                        profileImageSource = userProfile.photoUrl,
                        isSaving = false,
                        errorMessage = null
                    )
                }
        }
    }
}