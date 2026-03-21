package com.example.recipebook.application

import android.app.Application
import com.example.recipebook.domain.useCase.settings.observeTheme.ObserveThemeUseCase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltAndroidApp
class RecipeBookApp : Application() {
    @Inject
    lateinit var observeThemeUseCase: ObserveThemeUseCase

    override fun onCreate() {
        super.onCreate()
        runBlocking {
            observeThemeUseCase.execute()
        }
    }
}