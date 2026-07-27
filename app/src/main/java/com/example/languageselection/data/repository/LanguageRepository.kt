package com.example.languageselection.data.repository

import com.example.languageselection.R
import com.example.languageselection.data.model.Language
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface LanguageRepository {
    fun getAvailableLanguages(): Flow<List<Language>>
}

class LanguageRepositoryImpl : LanguageRepository {
    override fun getAvailableLanguages(): Flow<List<Language>> = flow {
        val languages = listOf(
            Language("en", R.string.lang_en),
            Language("es", R.string.lang_es),
            Language("sv", R.string.lang_sv),
            Language("fi", R.string.lang_fi)
        )
        emit(languages)
    }.flowOn(Dispatchers.IO)
}
