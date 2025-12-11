package com.example.recipebook.domain.repository

interface SettingsRepository {
    fun getSystemLanguage(): String?
    fun changeLanguage(value: String?)
}