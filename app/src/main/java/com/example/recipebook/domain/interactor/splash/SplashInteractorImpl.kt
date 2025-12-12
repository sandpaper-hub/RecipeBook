package com.example.recipebook.domain.interactor.splash

import com.example.recipebook.domain.useCase.ChangeApplicationLanguageUseCase
import com.example.recipebook.domain.useCase.CheckIsLoggedInUseCase
import com.example.recipebook.domain.useCase.GetSavedLanguageUseCase
import com.example.recipebook.domain.useCase.GetSystemLanguageUseCase
import javax.inject.Inject

class SplashInteractorImpl @Inject constructor(
    private val getSavedLanguageUseCase: GetSavedLanguageUseCase,
    private val changeApplicationLanguageUseCase: ChangeApplicationLanguageUseCase,
    private val getSystemLanguageUseCase: GetSystemLanguageUseCase,
    private val checkIsLoggedInUseCase: CheckIsLoggedInUseCase
) : SplashInteractor {
    override fun isLoggedIn(): Boolean =
        checkIsLoggedInUseCase.execute()

    override suspend fun setSystemLanguage() {
        val savedLanguage = getSavedLanguageUseCase.getOnce()
        if (!savedLanguage.isNullOrBlank()) {
            changeApplicationLanguageUseCase.execute(savedLanguage)
        } else {
            val systemLanguage = getSystemLanguageUseCase.execute()
            changeApplicationLanguageUseCase.execute(systemLanguage)
        }
    }

    override suspend fun changeApplicationLanguage(value: String?) {
        changeApplicationLanguageUseCase.execute(value)
    }
}