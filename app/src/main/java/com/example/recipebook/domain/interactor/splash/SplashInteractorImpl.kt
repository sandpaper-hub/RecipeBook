package com.example.recipebook.domain.interactor.splash

import com.example.recipebook.domain.repository.AuthenticationRepository
import com.example.recipebook.domain.useCase.ChangeApplicationLanguageUseCase
import com.example.recipebook.domain.useCase.GetSavedLanguageUseCase
import com.example.recipebook.domain.useCase.GetSystemLanguageUseCase
import javax.inject.Inject

class SplashInteractorImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val getSavedLanguageUseCase: GetSavedLanguageUseCase,
    private val changeApplicationLanguageUseCase: ChangeApplicationLanguageUseCase,
    private val getSystemLanguageUseCase: GetSystemLanguageUseCase
) : SplashInteractor {
    override fun isLoggedIn(): Boolean =
        authenticationRepository.isLoggedIn()

    override suspend fun getOnce(): String? = getSavedLanguageUseCase.getOnce()
    override suspend fun changeApplicationLanguage(value: String?) {
        changeApplicationLanguageUseCase.execute(value)
    }
    override fun getSystemLanguage(): String? {
        return getSystemLanguageUseCase.execute()
    }


}