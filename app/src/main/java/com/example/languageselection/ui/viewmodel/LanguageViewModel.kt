package com.example.languageselection.ui.viewmodel

import android.util.Log
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
        // No need to call loadLanguages() upon View construction.
        // LanguageSelectionScreen will call the method upon re-composing due to configuration change.
        //loadLanguages()
    }

    /**
     * Get the list with the current locale re-ordered as the 1st item.
     */
    public fun loadLanguages() {
        viewModelScope.launch {
            repository.getAvailableLanguages().collect {

                val ml = it.toMutableList()

                // re-order: place the current the system locale at the 1st item in the list
                val currentLocales = AppCompatDelegate.getApplicationLocales()
                val currentLanguageCode = if (!currentLocales.isEmpty) currentLocales.get(0)?.language else "en"
                val i = it.indexOfFirst {
                    it.code == currentLanguageCode
                }
                // move item from index i to position 0
                if (i > 0) {
                    ml.removeAt(i)
                    ml.add(0, it[i])
                }
                Log.d(TAG, "ml=${ml}")

                // update the MutableStateFlow with the re-ordered list
                _languages.value = ml
            }
        }
    }

    /**
     * Trigger the system-level configuration change per the language.
     * No-op behavior if the new LocaleListCompat is the same as the currently active one.
     */
    fun selectLanguage(language: Language) {
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(language.code)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }

    companion object {

        const val TAG = "LanguageViewModel"

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
