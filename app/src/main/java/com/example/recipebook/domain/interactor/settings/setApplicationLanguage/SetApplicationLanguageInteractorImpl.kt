package com.example.recipebook.domain.interactor.settings.setApplicationLanguage

import com.example.recipebook.domain.useCase.settings.changeApplicationLanguage.ChangeApplicationLanguageUseCaseImpl
import com.example.recipebook.domain.useCase.settings.observeSavedLanguage.ObserveSavedLanguageUseCaseImpl
import com.example.recipebook.domain.useCase.settings.getSystemLanguage.GetSystemLanguageUseCaseImpl
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SetApplicationLanguageInteractorImpl @Inject constructor(
    private val observeSavedLanguageUseCaseImpl: ObserveSavedLanguageUseCaseImpl,
    private val changeApplicationLanguageUseCaseImpl: ChangeApplicationLanguageUseCaseImpl,
    private val getSystemLanguageUseCaseImpl: GetSystemLanguageUseCaseImpl,
) : SetApplicationLanguageInteractor {

    override suspend fun invoke() {
        val savedLanguage = observeSavedLanguageUseCaseImpl.execute().first()
        if (!savedLanguage.isNullOrBlank()) {
            changeApplicationLanguageUseCaseImpl.execute(savedLanguage)
        } else {
            val systemLanguage = getSystemLanguageUseCaseImpl.execute()
            changeApplicationLanguageUseCaseImpl.execute(systemLanguage)
        }
    }
}