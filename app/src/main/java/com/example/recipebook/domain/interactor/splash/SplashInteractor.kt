package com.example.recipebook.domain.interactor.splash

interface SplashInteractor {
    fun isLoggedIn(): Boolean
    suspend fun getOnce(): String?
    suspend fun changeApplicationLanguage(value: String?)
    fun getSystemLanguage(): String?
}