package com.example.recipebook.domain.interactor.splash

import com.example.recipebook.domain.repository.FirebaseRepository
import javax.inject.Inject

class SplashInteractorImpl @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : SplashInteractor {
    override fun isLoggedIn(): Boolean =
        firebaseRepository.isLoggedIn()
}