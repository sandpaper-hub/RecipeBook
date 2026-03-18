package com.example.recipebook.presentation.viewModel.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.splash.SplashInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val splashInteractor: SplashInteractor
) : ViewModel() {
    private val _event = MutableSharedFlow<SplashEvent>()
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            splashInteractor.setSystemLanguage()
            val isLoggedIn = splashInteractor.isLoggedIn()

            _event.emit(
                value = if (isLoggedIn) {
                    SplashEvent.OnHome
                } else {
                    SplashEvent.OnOnboarding
                }
            )
        }
    }
}