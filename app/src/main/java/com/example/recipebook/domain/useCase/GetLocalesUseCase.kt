package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.repository.LocaleRepository
import javax.inject.Inject

class GetLocalesUseCase @Inject constructor(
   private val localeRepository: LocaleRepository
) {
    fun execute(): List<String> = localeRepository.getCountryLocales()
}