package com.example.recipebook.domain.interactor.profile

import com.example.recipebook.domain.model.profile.UserProfile
import com.example.recipebook.domain.model.recipe.Recipe
import com.example.recipebook.domain.useCase.GetLocalesUseCase
import com.example.recipebook.domain.useCase.GetUserIdFlowUseCase
import com.example.recipebook.domain.useCase.GetUserRecipesUseCase
import com.example.recipebook.domain.useCase.UpdateUserProfileUseCase
import com.example.recipebook.domain.useCase.GetUserProfileUseCase
import com.example.recipebook.domain.util.ImageCompressor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileInteractorImpl @Inject constructor(
    private val imageCompressor: ImageCompressor,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
    private val getLocalesUseCase: GetLocalesUseCase,
    private val getUserRecipesUseCase: GetUserRecipesUseCase,
    private val getUserIdFlowUseCase: GetUserIdFlowUseCase
) : ProfileInteractor {

    override fun observerUserProfile(): Flow<UserProfile> =
        getUserProfileUseCase.execute()


    override suspend fun updateUserData(data: Map<String, Any?>, uriString: String?): Result<Unit> {
        if (uriString == null) {
            return updateUserProfileUseCase.execute(data, null)
        } else {
            val imageBytes = imageCompressor.compress(uriString)
            return updateUserProfileUseCase.execute(data, imageBytes)
        }
    }

    override fun getLocales(): List<String> =
        getLocalesUseCase.execute()

    override fun observeUserRecipes(userId: String): Flow<List<Recipe>> =
        getUserRecipesUseCase.execute(userId)

    override fun getUserIdFlow(): Flow<String?> = getUserIdFlowUseCase.execute()
}