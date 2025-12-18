package com.example.recipebook.domain.repository

interface LocaleRepository {
    fun getCountryLocales() : List<String>
}