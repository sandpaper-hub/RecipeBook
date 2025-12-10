package com.example.recipebook.domain.interactor.settings

interface SettingsInteractor {
    fun getSystemLanguage(): String?
    fun changeApplicationLanguage(value: String)
}