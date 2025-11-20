package com.example.recipebook.presentation.viewModel.splashScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.SplashInteractor
import com.example.recipebook.navigation.rootNavGraph.Auth
import com.example.recipebook.navigation.rootNavGraph.Home
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val splashInteractor: SplashInteractor
) : ViewModel() {
    val startDestination = mutableStateOf<String?>(null)

    init {
        viewModelScope.launch {
            val isLoggedIn = splashInteractor.isLoggedIn()
            startDestination.value = if (isLoggedIn) {
                Home.MainHome.route
            } else {
                Auth.Onboarding.route
            }
        }
    }
}