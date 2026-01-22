package com.example.recipebook.presentation.viewModel.accountScreen

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
class AccountViewModel @Inject constructor(
    private val profileInteractor: ProfileInteractor
) : ViewModel() {
    var uiState by mutableStateOf(AccountUiState())
        private set

    init {
        observeUserProfile()
        uiState = uiState.copy(regionLocales = profileInteractor.getLocales())
    }

    val allowedRegex = Regex("^[A-Za-z0-9._]*$")

    private fun observeUserProfile() {
        viewModelScope.launch {
            profileInteractor.observerUserProfile()
                .catch { error ->
                    uiState = uiState.copy(errorMessage = error.message)
                }
                .collect { userProfile ->
                    uiState = uiState.copy(
                        fullName = userProfile.fullName,
                        nickName = userProfile.nickName,
                        region = userProfile.region,
                        profileImageSource = userProfile.photoUrl,
                        dateOfBirth = userProfile.dateOfBirth,
                        gender = userProfile.gender
                    )
                }
        }
    }

    fun onImagePicked(uri: Uri?) {
        uiState = uiState.copy(
            localImageSource = uri
        )
    }

    fun onNameChanged(newName: String) {
        uiState = uiState.copy(fullName = newName)
    }

    fun onNickNameChanged(newValue: String) {
        uiState = if (allowedRegex.matches(newValue)) {
            uiState.copy(nickName = newValue)
        } else {
            uiState.copy(errorMessage = "No specific symbols")
        }
    }

    fun showCountryMenu(isOpen: Boolean) {
        uiState = uiState.copy(showRegionMenu = isOpen)
    }

    fun onCountryChange(country: String) {
        uiState = uiState.copy(
            region = country,
            showRegionMenu = false
        )
    }

    fun showDatePicker(isOpen: Boolean) {
        uiState = uiState.copy(showDatePicker = isOpen)
    }

    fun selectConfirmedDate(value: Long?) {
        uiState = uiState.copy(
            dateOfBirth = value,
            showDatePicker = false
        )
    }

    fun onGenderChanged(newValue: String) {
        uiState = uiState.copy(gender = newValue)
    }

    fun onSaveClick(onBackNavigation: () -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(isSaving = true)

            val result = profileInteractor.updateUserData(
                data = mapOf(
                    "fullName" to uiState.fullName,
                    "nickName" to uiState.nickName,
                    "region" to uiState.region,
                    "dateOfBirth" to uiState.dateOfBirth,
                    "gender" to uiState.gender
                ),
                uriString = uiState.localImageSource?.toString()
            )
            delay(2000)
            uiState = if (result.isSuccess) {
                uiState.copy(
                    isSaving = false,
                    errorMessage = result.exceptionOrNull()?.message
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
}