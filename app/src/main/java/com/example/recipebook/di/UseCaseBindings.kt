package com.example.recipebook.di

import com.example.recipebook.domain.interactor.recipes.getRecipeSteps.GetRecipeStepsUseCase
import com.example.recipebook.domain.interactor.recipes.getRecipeSteps.GetRecipeStepsUseCaseImpl
import com.example.recipebook.domain.useCase.authentication.checkIsLoggedIn.CheckIsLoggedInUseCase
import com.example.recipebook.domain.useCase.authentication.checkIsLoggedIn.CheckIsLoggedInUseCaseImpl
import com.example.recipebook.domain.useCase.authentication.loginByEmail.LoginByEmailUseCase
import com.example.recipebook.domain.useCase.authentication.loginByEmail.LoginByEmailUseCaseImpl
import com.example.recipebook.domain.useCase.authentication.logout.LogOutUseCase
import com.example.recipebook.domain.useCase.authentication.logout.LogOutUseCaseImpl
import com.example.recipebook.domain.useCase.authentication.validateAuthenticationInput.ValidateAuthenticationInputUseCase
import com.example.recipebook.domain.useCase.authentication.validateAuthenticationInput.ValidateAuthenticationInputUseCaseImpl
import com.example.recipebook.domain.useCase.recipe.addRecipeToCollectionUseCase.AddRecipeIdToCollectionUseCaseImpl
import com.example.recipebook.domain.useCase.recipe.addRecipeToCollectionUseCase.AddRecipeToCollectionUseCase
import com.example.recipebook.domain.useCase.userProfile.getUserIdFlow.GetUserIdFlowUseCaseImpl
import com.example.recipebook.domain.useCase.createRandomId.CreateRandomIdUseCase
import com.example.recipebook.domain.useCase.createRandomId.CreateRandomIdUseCaseImpl
import com.example.recipebook.domain.useCase.collection.getUserCollectionUseCase.GetUserCollectionUseCase
import com.example.recipebook.domain.useCase.collection.getUserCollectionUseCase.GetUserCollectionUseCaseImpl
import com.example.recipebook.domain.useCase.collection.observeCollectionDetailUseCase.ObserveCollectionDetailUseCase
import com.example.recipebook.domain.useCase.collection.observeCollectionDetailUseCase.ObserveCollectionDetailUseCaseImpl
import com.example.recipebook.domain.useCase.collection.observeUserCollectionUseCase.ObserveUserCollectionUseCase
import com.example.recipebook.domain.useCase.collection.observeUserCollectionUseCase.ObserveUserCollectionUseCaseImpl
import com.example.recipebook.domain.useCase.recipe.removeRecipeFromCollectionUseCase.RemoveBrokenIdUseCase
import com.example.recipebook.domain.useCase.recipe.removeRecipeFromCollectionUseCase.RemoveBrokenIdUseCaseImpl
import com.example.recipebook.domain.useCase.userProfile.getUserIdFlow.GetUserIdFlowUseCase
import com.example.recipebook.domain.useCase.recipe.observeRecipeById.ObserveRecipeByIdUseCase
import com.example.recipebook.domain.useCase.recipe.observeRecipeById.ObserveRecipeByIdUseCaseImpl
import com.example.recipebook.domain.useCase.recipe.getRecipeListByIds.GetRecipeListByIdsUseCase
import com.example.recipebook.domain.useCase.recipe.getRecipeListByIds.GetRecipeListByIdsUseCaseImpl
import com.example.recipebook.domain.useCase.recipe.observeRecipeListByIds.ObserveRecipeListByIdsUseCase
import com.example.recipebook.domain.useCase.recipe.observeRecipeListByIds.ObserveRecipeListByIdsUseCaseImpl
import com.example.recipebook.domain.useCase.recipe.searchRecipe.SearchRecipeUseCase
import com.example.recipebook.domain.useCase.recipe.searchRecipe.SearchRecipeUseCaseImpl
import com.example.recipebook.domain.useCase.settings.changeApplicationLanguage.ChangeApplicationLanguageUseCase
import com.example.recipebook.domain.useCase.settings.changeApplicationLanguage.ChangeApplicationLanguageUseCaseImpl
import com.example.recipebook.domain.useCase.settings.changeTheme.ChangeThemeUseCase
import com.example.recipebook.domain.useCase.settings.changeTheme.ChangeThemeUseCaseImpl
import com.example.recipebook.domain.useCase.settings.getSystemLanguage.GetSystemLanguageUseCase
import com.example.recipebook.domain.useCase.settings.getSystemLanguage.GetSystemLanguageUseCaseImpl
import com.example.recipebook.domain.useCase.settings.observeSavedLanguage.ObserveSavedLanguageUseCase
import com.example.recipebook.domain.useCase.settings.observeSavedLanguage.ObserveSavedLanguageUseCaseImpl
import com.example.recipebook.domain.useCase.settings.observeTheme.ObserveThemeUseCase
import com.example.recipebook.domain.useCase.settings.observeTheme.ObserveThemeUseCaseImpl
import com.example.recipebook.domain.useCase.userProfile.getLocales.GetLocalesUseCase
import com.example.recipebook.domain.useCase.userProfile.getLocales.GetLocalesUseCaseImpl
import com.example.recipebook.domain.useCase.userProfile.observeUserProfile.ObserveUserProfileUseCase
import com.example.recipebook.domain.useCase.userProfile.observeUserProfile.ObserveUserProfileUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseBindings {

    @Binds
    abstract fun bindCreateRandomIdUseCase(
        impl: CreateRandomIdUseCaseImpl
    ): CreateRandomIdUseCase

    @Binds
    abstract fun bindGetUserCollectionUseCase(
        impl: GetUserCollectionUseCaseImpl
    ): GetUserCollectionUseCase

    @Binds
    abstract fun bindGetRecipeByIdUseCase(
        impl: ObserveRecipeByIdUseCaseImpl
    ): ObserveRecipeByIdUseCase

    @Binds
    abstract fun bindGetUserIdFlowUseCase(
        impl: GetUserIdFlowUseCaseImpl
    ): GetUserIdFlowUseCase

    @Binds
    abstract  fun bindObserveUserCollectionUseCase(
        impl: ObserveUserCollectionUseCaseImpl
    ): ObserveUserCollectionUseCase

    @Binds
    abstract fun bindAddRecipeToCollectionUseCase(
        impl: AddRecipeIdToCollectionUseCaseImpl
    ): AddRecipeToCollectionUseCase

    @Binds
    abstract fun bindRemoveRecipeFromCollectionUseCase(
        impl: RemoveBrokenIdUseCaseImpl
    ): RemoveBrokenIdUseCase

    @Binds
    abstract fun bindObserveCollectionDetailUseCase(
        impl: ObserveCollectionDetailUseCaseImpl
    ): ObserveCollectionDetailUseCase

    @Binds
    abstract fun bindGetRecipeListByIdsUseCase(
        impl: GetRecipeListByIdsUseCaseImpl
    ): GetRecipeListByIdsUseCase

    @Binds
    abstract fun bindSearchRecipeUseCase(
        impl: SearchRecipeUseCaseImpl
    ): SearchRecipeUseCase

    @Binds
    abstract fun bindValidateLoginInputUseCase(
        impl: ValidateAuthenticationInputUseCaseImpl
    ): ValidateAuthenticationInputUseCase

    @Binds
    abstract fun bindLoginByEmailUseCase(
        impl: LoginByEmailUseCaseImpl
    ): LoginByEmailUseCase

    @Binds
    abstract  fun bindObserveUserProfileUseCase(
        impl: ObserveUserProfileUseCaseImpl
    ): ObserveUserProfileUseCase

    @Binds
    abstract fun bindGetLocalesUseCase(
        impl: GetLocalesUseCaseImpl
    ): GetLocalesUseCase

    @Binds
    abstract fun bindGetRecipeStepsUseCase(
        impl: GetRecipeStepsUseCaseImpl
    ): GetRecipeStepsUseCase

    @Binds
    abstract fun bindGetSystemLanguageUseCase(
        impl: GetSystemLanguageUseCaseImpl
    ): GetSystemLanguageUseCase

    @Binds
    abstract fun bindChangeApplicationLanguageUseCase(
        impl: ChangeApplicationLanguageUseCaseImpl
    ): ChangeApplicationLanguageUseCase

    @Binds
    abstract fun bindObserveSavedLanguageUseCase(
        impl: ObserveSavedLanguageUseCaseImpl
    ): ObserveSavedLanguageUseCase

    @Binds
    abstract fun bindObserveRecipeListByIdsUseCase(
        impl: ObserveRecipeListByIdsUseCaseImpl
    ): ObserveRecipeListByIdsUseCase

    @Binds
    abstract fun bindObserveThemeUseCase(
        impl: ObserveThemeUseCaseImpl
    ): ObserveThemeUseCase

    @Binds
    abstract fun bindLogOutUseCase(
        impl: LogOutUseCaseImpl
    ): LogOutUseCase

    @Binds
    abstract fun bindCheckIsLoggedInUseCase(
        impl: CheckIsLoggedInUseCaseImpl
    ): CheckIsLoggedInUseCase

    @Binds
    abstract fun bindChangeThemeUseCase(
        impl: ChangeThemeUseCaseImpl
    ): ChangeThemeUseCase
}