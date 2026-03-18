package com.example.recipebook.presentation.viewModel.accountScreen

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.profile.updateProfile.UpdateUserDataInteractor
import com.example.recipebook.domain.useCase.userProfile.getLocales.GetLocalesUseCase
import com.example.recipebook.domain.useCase.userProfile.observeUserProfile.ObserveUserProfileUseCase
import com.example.recipebook.presentation.util.toDomain
import com.example.recipebook.presentation.viewModel.accountScreen.model.AccountUiEvent
import com.example.recipebook.presentation.viewModel.accountScreen.model.AccountUiState
import com.example.recipebook.presentation.viewModel.model.ImageSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val observeUserProfileUseCase: ObserveUserProfileUseCase,
    private val getLocalesUseCase: GetLocalesUseCase,
    private val updateUserDataInteractor: UpdateUserDataInteractor
) : ViewModel() {

    private val _uiEvents = MutableSharedFlow<AccountUiEvent>()
    val uiEvents = _uiEvents.asSharedFlow()
    private val _uiState = MutableStateFlow(AccountUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeUserProfile()
        initRegionLocales()
    }

    val allowedRegex = Regex("^[A-Za-z0-9._]*$")

    private fun observeUserProfile() {
        viewModelScope.launch {
            observeUserProfileUseCase.execute()
                .catch { error ->
                    _uiState.update {
                        it.copy(errorMessage = error.message)
                    }
                }
                .collect { userProfile ->
                    _uiState.update {
                        it.copy(
                            fullName = userProfile.fullName,
                            nickName = userProfile.nickName,
                            region = userProfile.region,
                            profileImageSource = if (userProfile.photoUrl == null) {
                                ImageSource.None
                            } else {
                                ImageSource.Remote(userProfile.photoUrl)
                            },
                            dateOfBirth = userProfile.dateOfBirth,
                            gender = userProfile.gender
                        )
                    }
                }
        }
    }

    private fun initRegionLocales() {
        _uiState.update {
            it.copy(regionLocales = getLocalesUseCase.execute())
        }
    }

    fun onImagePicked(uri: Uri?) {
        _uiState.update {
            it.copy(
                profileImageSource = if (uri == null) {
                    ImageSource.None
                } else {
                    ImageSource.Local(uri.toString())
                }
            )
        }
    }

    fun onNameChanged(newName: String) {
        _uiState.update {
            it.copy(fullName = newName)
        }
    }

    fun onNickNameChanged(newValue: String) {
        _uiState.update {
            if (allowedRegex.matches(newValue)) {
                it.copy(nickName = newValue)
            } else {
                it.copy(errorMessage = "No specific symbols")
            }
        }
    }

    fun showCountryMenu(isOpen: Boolean) {
        _uiState.update {
            it.copy(showRegionMenu = isOpen)
        }
    }

    fun onCountryChange(country: String) {
        _uiState.update {
            it.copy(
                region = country,
                showRegionMenu = false
            )
        }
    }

    fun showDatePicker(isOpen: Boolean) {
        _uiState.update {
            it.copy(showDatePicker = isOpen)
        }
    }

    fun selectConfirmedDate(value: Long?) {
        _uiState.update {
            it.copy(
                dateOfBirth = value,
                showDatePicker = false
            )
        }
    }

    fun onGenderChanged(newValue: String) {
        _uiState.update {
            it.copy(gender = newValue)
        }
    }

    fun onSaveClick() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isSaving = true)
            }

            val result = updateUserDataInteractor.invoke(
                data = mapOf(
                    "fullName" to uiState.value.fullName,
                    "nickName" to uiState.value.nickName,
                    "region" to uiState.value.region,
                    "dateOfBirth" to uiState.value.dateOfBirth,
                    "gender" to uiState.value.gender
                ),
                imageSource = _uiState.value.profileImageSource.toDomain()
            )
            delay(2000)
            _uiState.update {
                if (result.isSuccess) {
                    it.copy(
                        errorMessage = result.exceptionOrNull()?.message
                    )
                } else {
                    it.copy(
                        isSaving = false,
                        errorMessage = result.exceptionOrNull()?.message
                    )
                }
            }
            if (result.isSuccess) {
                onBack()
            }
        }
    }

    fun onBack() {
        viewModelScope.launch {
            _uiEvents.emit(AccountUiEvent.GoBack)
        }
    }
}