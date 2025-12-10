package com.example.recipebook.domain.interactor.settings

import com.example.recipebook.domain.useCase.ChangeApplicationLanguageUseCase
import com.example.recipebook.domain.useCase.GetSystemLanguageUseCase
import javax.inject.Inject

class SettingsInteractorImpl @Inject constructor(
    private val getSystemLanguageUseCase: GetSystemLanguageUseCase,
    private val changeApplicationLanguageUseCase: ChangeApplicationLanguageUseCase
): SettingsInteractor {

    override fun getSystemLanguage(): String? {
        return getSystemLanguageUseCase.execute()
    }

    override fun changeApplicationLanguage(value: String) {
        changeApplicationLanguageUseCase.execute(value)
    }
}