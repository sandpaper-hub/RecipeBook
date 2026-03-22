package com.example.recipebook.di

import com.example.recipebook.domain.interactor.collection.createCollectionInteractor.CreateCollectionInteractor
import com.example.recipebook.domain.interactor.collection.createCollectionInteractor.CreateCollectionInteractorImpl
import com.example.recipebook.domain.interactor.collection.deleteCollectionInteractor.DeleteCollectionInteractor
import com.example.recipebook.domain.interactor.collection.deleteCollectionInteractor.DeleteCollectionInteractorImpl
import com.example.recipebook.domain.interactor.collection.updateCollectionInteractor.UpdateCollectionInteractor
import com.example.recipebook.domain.interactor.collection.updateCollectionInteractor.UpdateCollectionInteractorImpl
import com.example.recipebook.domain.interactor.profile.updateProfile.UpdateUserDataInteractor
import com.example.recipebook.domain.interactor.profile.updateProfile.UpdateUserDataInteractorImpl
import com.example.recipebook.domain.interactor.recipes.fullRecipeInteractor.FullRecipeInteractor
import com.example.recipebook.domain.interactor.recipes.fullRecipeInteractor.FullRecipeInteractorImpl
import com.example.recipebook.domain.interactor.settings.setApplicationLanguage.SetApplicationLanguageInteractor
import com.example.recipebook.domain.interactor.settings.setApplicationLanguage.SetApplicationLanguageInteractorImpl
import com.example.recipebook.domain.interactor.recipes.createNewRecipe.CreateNewRecipeInteractor
import com.example.recipebook.domain.interactor.recipes.createNewRecipe.CreateNewRecipeInteractorImpl
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
    abstract fun bindSplashInteractor(
        impl: SetApplicationLanguageInteractorImpl
    ): SetApplicationLanguageInteractor

    @Binds
    abstract fun bindCollectionInteractor(
        impl: CreateCollectionInteractorImpl
    ): CreateCollectionInteractor

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

    @Binds
    abstract fun bindUpdateUserDataInteractor(
        impl: UpdateUserDataInteractorImpl
    ): UpdateUserDataInteractor

    @Binds
    abstract fun bindCreateNewRecipeInteractor(
        impl: CreateNewRecipeInteractorImpl
    ): CreateNewRecipeInteractor
}