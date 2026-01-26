package com.example.recipebook.di

import com.example.recipebook.domain.interactor.collection.CollectionInteractor
import com.example.recipebook.domain.interactor.collection.CollectionInteractorImpl
import com.example.recipebook.domain.interactor.login.LoginInteractor
import com.example.recipebook.domain.interactor.login.LoginInteractorImpl
import com.example.recipebook.domain.interactor.profile.ProfileInteractor
import com.example.recipebook.domain.interactor.profile.ProfileInteractorImpl
import com.example.recipebook.domain.interactor.registration.RegistrationInteractor
import com.example.recipebook.domain.interactor.registration.RegistrationInteractorImpl
import com.example.recipebook.domain.interactor.settings.SettingsInteractor
import com.example.recipebook.domain.interactor.settings.SettingsInteractorImpl
import com.example.recipebook.domain.interactor.splash.SplashInteractor
import com.example.recipebook.domain.interactor.splash.SplashInteractorImpl
import com.example.recipebook.domain.interactor.recipes.RecipesInteractor
import com.example.recipebook.domain.interactor.recipes.RecipesInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class InteractorBindings {


    @Binds
    abstract fun bingRegistrationInteractor(
        impl: RegistrationInteractorImpl
    ): RegistrationInteractor

    @Binds
    abstract fun bindLoginInteractor(
        impl: LoginInteractorImpl
    ): LoginInteractor

    @Binds
    abstract fun bindSplashInteractor(
        impl: SplashInteractorImpl
    ): SplashInteractor

    @Binds
    abstract fun bindProfileInteractor(
        impl: ProfileInteractorImpl
    ): ProfileInteractor

    @Binds
    abstract fun bindSettingsInteractor(
        impl: SettingsInteractorImpl
    ): SettingsInteractor

    @Binds
    abstract fun bindUploadRecipeInteractor(
        impl: RecipesInteractorImpl
    ): RecipesInteractor

    @Binds
    abstract fun bindCollectionInteractor(
        impl: CollectionInteractorImpl
    ): CollectionInteractor
}