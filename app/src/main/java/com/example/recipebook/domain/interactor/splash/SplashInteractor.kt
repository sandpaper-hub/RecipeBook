package com.example.recipebook.domain.interactor.splash

interface SplashInteractor {
    fun isLoggedIn(): Boolean
    suspend fun setSystemLanguage()
    suspend fun changeApplicationLanguage(value: String?)
}