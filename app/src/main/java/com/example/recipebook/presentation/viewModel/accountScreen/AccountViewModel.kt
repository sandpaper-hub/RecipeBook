package com.example.recipebook.presentation.viewModel.accountScreen

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

    private fun observeUserProfile() {
        viewModelScope.launch {
            profileInteractor.observerUserProfile()
                .catch { error ->
                    uiState = uiState.copy(errorMessage = error.message)
                }
                .collect { userProfile ->
                    uiState = uiState.copy(
                        fullName = userProfile.fullName,
                        region = userProfile.region,
                        dateOfBirth = userProfile.dateOfBirth,
                        gender = userProfile.gender
                    )
                }
        }
    }

    fun onNameChanged(newName: String) {
        uiState = uiState.copy(fullName = newName)
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
                    "region" to uiState.region,
                    "dateOfBirth" to uiState.dateOfBirth,
                    "gender" to uiState.gender
                ),
                uriString = null
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