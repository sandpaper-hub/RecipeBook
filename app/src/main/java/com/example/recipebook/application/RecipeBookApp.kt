package com.example.recipebook.application

import android.app.Application
import com.example.recipebook.domain.interactor.settings.SettingsInteractor
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltAndroidApp
class RecipeBookApp: Application(){
    @Inject lateinit var settingsInteractor: SettingsInteractor

    override fun onCreate() {
        super.onCreate()
        runBlocking {
            settingsInteractor.getTheme()
        }
    }
}