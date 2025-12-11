package com.example.recipebook.domain.interactor.settings

import com.example.recipebook.domain.model.ThemeMode
import com.example.recipebook.domain.useCase.ChangeApplicationLanguageUseCase
import com.example.recipebook.domain.useCase.ChangeThemeUseCase
import com.example.recipebook.domain.useCase.GetSavedLanguageUseCase
import com.example.recipebook.domain.useCase.GetSystemLanguageUseCase
import com.example.recipebook.domain.useCase.GetThemeUseCase
import com.example.recipebook.domain.useCase.LogOutUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsInteractorImpl @Inject constructor(
    private val getSystemLanguageUseCase: GetSystemLanguageUseCase,
    private val changeApplicationLanguageUseCase: ChangeApplicationLanguageUseCase,
    private val getSavedLanguageUseCase: GetSavedLanguageUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val changeThemeUseCase: ChangeThemeUseCase,
    private val getThemeUseCase: GetThemeUseCase,
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

    override fun getTheme(): Flow<ThemeMode>  = getThemeUseCase.execute()

    override suspend fun changeTheme(mode: ThemeMode) {
        changeThemeUseCase.execute(mode)
    }
}