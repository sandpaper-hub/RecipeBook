package com.example.recipebook.di

import com.example.recipebook.domain.useCase.recipe.addRecipeToCollectionUseCase.AddRecipeIdToCollectionUseCaseImpl
import com.example.recipebook.domain.useCase.recipe.addRecipeToCollectionUseCase.AddRecipeToCollectionUseCase
import com.example.recipebook.domain.useCase.getUserIdFlow.GetUserIdFlowUseCaseImpl
import com.example.recipebook.domain.useCase.createRandomId.CreateRandomIdUseCase
import com.example.recipebook.domain.useCase.createRandomId.CreateRandomIdUseCaseImpl
import com.example.recipebook.domain.useCase.collection.getUserCollectionUseCase.GetUserCollectionUseCase
import com.example.recipebook.domain.useCase.collection.getUserCollectionUseCase.GetUserCollectionUseCaseImpl
import com.example.recipebook.domain.useCase.collection.observeUserCollectionUseCase.ObserveUserCollectionUseCase
import com.example.recipebook.domain.useCase.collection.observeUserCollectionUseCase.ObserveUserCollectionUseCaseImpl
import com.example.recipebook.domain.useCase.recipe.removeRecipeFromCollectionUseCase.RemoveRecipeFromCollectionUseCase
import com.example.recipebook.domain.useCase.recipe.removeRecipeFromCollectionUseCase.RemoveRecipeFromCollectionUseCaseImpl
import com.example.recipebook.domain.useCase.getUserIdFlow.GetUserIdFlowUseCase
import com.example.recipebook.domain.useCase.recipe.deleteRecipe.DeleteRecipeUseCase
import com.example.recipebook.domain.useCase.recipe.deleteRecipe.DeleteRecipeUseCaseImpl
import com.example.recipebook.domain.useCase.recipe.getRecipeById.GetRecipeByIdUseCase
import com.example.recipebook.domain.useCase.recipe.getRecipeById.GetRecipeByIdUseCaseImpl
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
        impl: GetRecipeByIdUseCaseImpl
    ): GetRecipeByIdUseCase

    @Binds
    abstract fun bindDeleteRecipeUseCase(
        impl: DeleteRecipeUseCaseImpl
    ): DeleteRecipeUseCase

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
        impl: RemoveRecipeFromCollectionUseCaseImpl
    ): RemoveRecipeFromCollectionUseCase
}