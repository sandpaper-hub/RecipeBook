package com.example.recipebook.domain.interactor.profile

import com.example.recipebook.domain.useCase.GetLocalesUseCase
import com.example.recipebook.domain.useCase.userProfile.getUserIdFlow.GetUserIdFlowUseCaseImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileInteractorImpl @Inject constructor(
    private val getLocalesUseCase: GetLocalesUseCase,
    private val getUserIdFlowUseCaseImpl: GetUserIdFlowUseCaseImpl
) : ProfileInteractor {


    override fun getLocales(): List<String> =
        getLocalesUseCase.execute()

    override fun getUserIdFlow(): Flow<String?> = getUserIdFlowUseCaseImpl.execute()
}