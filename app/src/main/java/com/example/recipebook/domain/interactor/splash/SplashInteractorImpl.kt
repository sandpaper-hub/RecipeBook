package com.example.recipebook.domain.interactor.splash

import com.example.recipebook.domain.repository.AuthenticationRepository
import javax.inject.Inject

class SplashInteractorImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : SplashInteractor {
    override fun isLoggedIn(): Boolean =
        authenticationRepository.isLoggedIn()
}