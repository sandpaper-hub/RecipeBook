package com.example.recipebook.data.repository

import android.icu.util.ULocale
import com.example.recipebook.domain.repository.LocaleRepository
import java.util.Locale
import javax.inject.Inject

class LocaleRepositoryImpl @Inject constructor(): LocaleRepository {
    override fun getCountryLocales(): List<String> {
        return Locale.getISOCountries().map { country ->
            ULocale("", country).displayCountry
        }.sorted()
    }

}