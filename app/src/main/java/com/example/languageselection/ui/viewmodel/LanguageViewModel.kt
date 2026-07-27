package com.example.languageselection.ui.viewmodel

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import com.example.languageselection.data.model.Language
import com.example.languageselection.data.repository.LanguageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LanguageViewModel(private val repository: LanguageRepository) : ViewModel() {

    private val _languages = MutableStateFlow<List<Language>>(emptyList())
    val languages: StateFlow<List<Language>> = _languages.asStateFlow()

    init {
        loadLanguages()
    }

    private fun loadLanguages() {
        viewModelScope.launch {
            repository.getAvailableLanguages().collect {
                _languages.value = it
            }
        }
    }

    fun selectLanguage(language: Language) {
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(language.code)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }

    companion object {
        // API for the View layer to create the LanguageViewModel,encapsulating ViewModelProvider logic.
        fun provide(owner: ViewModelStoreOwner, repository: LanguageRepository): LanguageViewModel {
            return ViewModelProvider(owner, Factory(repository))[LanguageViewModel::class.java]
        }
    }

    class Factory(private val repository: LanguageRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LanguageViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LanguageViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
