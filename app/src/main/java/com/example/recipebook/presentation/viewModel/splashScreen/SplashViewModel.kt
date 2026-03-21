package com.example.recipebook.presentation.viewModel.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.settings.setApplicationLanguage.SetApplicationLanguageInteractor
import com.example.recipebook.domain.useCase.authentication.checkIsLoggedIn.CheckIsLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkIsLoggedInUseCase: CheckIsLoggedInUseCase,
    private val setApplicationLanguageInteractor: SetApplicationLanguageInteractor
) : ViewModel() {
    private val _event = MutableSharedFlow<SplashEvent>()
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            setApplicationLanguageInteractor.invoke()
            val isLoggedIn = checkIsLoggedInUseCase.execute()

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