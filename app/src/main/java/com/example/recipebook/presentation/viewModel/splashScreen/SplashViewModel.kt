package com.example.recipebook.presentation.viewModel.splashScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.splash.SplashInteractor
import com.example.recipebook.navigation.Graph
import com.example.recipebook.navigation.rootNavGraph.authenticationGraph.AuthenticationRoutes
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
            getLanguageOnce()
            val isLoggedIn = splashInteractor.isLoggedIn()
            startDestination.value = if (isLoggedIn) {
                Graph.MAIN_HOME
            } else {
                AuthenticationRoutes.Onboarding.route
            }
        }
    }

    suspend fun getLanguageOnce() {
        val savedLang = splashInteractor.getOnce()
        if (!savedLang.isNullOrBlank()) {
            splashInteractor.changeApplicationLanguage(savedLang)
        } else {
            val systemLanguage = splashInteractor.getSystemLanguage()
            splashInteractor.changeApplicationLanguage(systemLanguage)
        }
    }
}