package com.example.recipebook.domain.useCase.userProfile.getLocales

import com.example.recipebook.domain.repository.LocaleRepository
import javax.inject.Inject

class GetLocalesUseCaseImpl @Inject constructor(
   private val localeRepository: LocaleRepository
): GetLocalesUseCase {
    override fun execute(): List<String> = localeRepository.getCountryLocales()
}