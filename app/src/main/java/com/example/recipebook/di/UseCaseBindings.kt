package com.example.recipebook.di

import com.example.recipebook.domain.useCase.authentication.loginByEmail.LoginByEmailUseCase
import com.example.recipebook.domain.useCase.authentication.loginByEmail.LoginByEmailUseCaseImpl
import com.example.recipebook.domain.useCase.authentication.validateAuthenticationInput.ValidateAuthenticationInputUseCase
import com.example.recipebook.domain.useCase.authentication.validateAuthenticationInput.ValidateAuthenticationInputUseCaseImpl
import com.example.recipebook.domain.useCase.recipe.addRecipeToCollectionUseCase.AddRecipeIdToCollectionUseCaseImpl
import com.example.recipebook.domain.useCase.recipe.addRecipeToCollectionUseCase.AddRecipeToCollectionUseCase
import com.example.recipebook.domain.useCase.getUserIdFlow.GetUserIdFlowUseCaseImpl
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
import com.example.recipebook.domain.useCase.getUserIdFlow.GetUserIdFlowUseCase
import com.example.recipebook.domain.useCase.recipe.getRecipeById.GetRecipeByIdFlowUseCase
import com.example.recipebook.domain.useCase.recipe.getRecipeById.GetRecipeByIdFlowUseCaseImpl
import com.example.recipebook.domain.useCase.recipe.getRecipeListByIds.GetRecipeListByIdsUseCase
import com.example.recipebook.domain.useCase.recipe.getRecipeListByIds.GetRecipeListByIdsUseCaseImpl
import com.example.recipebook.domain.useCase.recipe.searchRecipe.SearchRecipeUseCase
import com.example.recipebook.domain.useCase.recipe.searchRecipe.SearchRecipeUseCaseImpl
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
        impl: GetRecipeByIdFlowUseCaseImpl
    ): GetRecipeByIdFlowUseCase

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
}