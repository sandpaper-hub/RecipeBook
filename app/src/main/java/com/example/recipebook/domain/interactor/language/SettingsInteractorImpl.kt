package com.example.recipebook.domain.interactor.language

import com.example.recipebook.domain.useCase.ChangeApplicationLanguageUseCase
import com.example.recipebook.domain.useCase.GetSavedLanguageUseCase
import com.example.recipebook.domain.useCase.GetSystemLanguageUseCase
import com.example.recipebook.domain.useCase.LogOutUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsInteractorImpl @Inject constructor(
    private val getSystemLanguageUseCase: GetSystemLanguageUseCase,
    private val changeApplicationLanguageUseCase: ChangeApplicationLanguageUseCase,
    private val getSavedLanguageUseCase: GetSavedLanguageUseCase,
    private val logOutUseCase: LogOutUseCase
): SettingsInteractor {
    override suspend fun observeSavedLanguage(): Flow<String?> =
        getSavedLanguageUseCase.observeSavedLanguage()


    override fun getSystemLanguage(): String? {
        return getSystemLanguageUseCase.execute()
    }

    override suspend fun changeApplicationLanguage(value: String) {
        changeApplicationLanguageUseCase.execute(value)
    }

    override suspend fun logOut() {
        logOutUseCase.execute()
    }
}