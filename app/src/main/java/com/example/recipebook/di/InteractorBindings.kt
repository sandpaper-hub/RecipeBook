package com.example.recipebook.di

import com.example.recipebook.domain.interactor.collection.CollectionInteractor
import com.example.recipebook.domain.interactor.collection.CollectionInteractorImpl
import com.example.recipebook.domain.interactor.collection.deleteCollectionInteractor.DeleteCollectionInteractor
import com.example.recipebook.domain.interactor.collection.deleteCollectionInteractor.DeleteCollectionInteractorImpl
import com.example.recipebook.domain.interactor.collection.updateCollectionInteractor.UpdateCollectionInteractor
import com.example.recipebook.domain.interactor.collection.updateCollectionInteractor.UpdateCollectionInteractorImpl
import com.example.recipebook.domain.interactor.login.LoginInteractor
import com.example.recipebook.domain.interactor.login.LoginInteractorImpl
import com.example.recipebook.domain.interactor.profile.ProfileInteractor
import com.example.recipebook.domain.interactor.profile.ProfileInteractorImpl
import com.example.recipebook.domain.interactor.recipes.fullRecipeInteractor.FullRecipeInteractor
import com.example.recipebook.domain.interactor.recipes.fullRecipeInteractor.FullRecipeInteractorImpl
import com.example.recipebook.domain.interactor.registration.RegistrationInteractor
import com.example.recipebook.domain.interactor.registration.RegistrationInteractorImpl
import com.example.recipebook.domain.interactor.settings.SettingsInteractor
import com.example.recipebook.domain.interactor.settings.SettingsInteractorImpl
import com.example.recipebook.domain.interactor.splash.SplashInteractor
import com.example.recipebook.domain.interactor.splash.SplashInteractorImpl
import com.example.recipebook.domain.interactor.recipes.RecipesInteractor
import com.example.recipebook.domain.interactor.recipes.RecipesInteractorImpl
import com.example.recipebook.domain.interactor.recipes.deleteRecipeInteractor.DeleteRecipeInteractor
import com.example.recipebook.domain.interactor.recipes.deleteRecipeInteractor.DeleteRecipeInteractorImpl
import com.example.recipebook.domain.interactor.recipes.updateRecipeInteractor.UpdateRecipeInteractor
import com.example.recipebook.domain.interactor.recipes.updateRecipeInteractor.UpdateRecipeInteractorImpl
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

    @Binds
    abstract fun bindFullRecipeInteractor(
        impl: FullRecipeInteractorImpl
    ): FullRecipeInteractor

    @Binds
    abstract fun bindUpdateRecipeInteractor(
        impl: UpdateRecipeInteractorImpl
    ): UpdateRecipeInteractor

    @Binds
    abstract fun bindUpdateCollectionInteractor(
        impl: UpdateCollectionInteractorImpl
    ): UpdateCollectionInteractor

    @Binds
    abstract fun bindDeleteRecipeInteractor(
        impl: DeleteRecipeInteractorImpl
    ): DeleteRecipeInteractor

    @Binds
    abstract fun bindDeleteCollectionInteractor(
        impl: DeleteCollectionInteractorImpl
    ): DeleteCollectionInteractor
}