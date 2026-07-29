package com.example.languageselection

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.os.LocaleListCompat
import com.example.languageselection.data.repository.LanguageRepositoryImpl
import com.example.languageselection.ui.LanguageSelectionScreen
import com.example.languageselection.ui.theme.LanguageSelectionTheme
import com.example.languageselection.ui.viewmodel.LanguageViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Default the selected language to English if not already set.
        if (AppCompatDelegate.getApplicationLocales().isEmpty) {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
        }

        // Layered architecture: View layer requests ViewModel via its provider API
        val repository = LanguageRepositoryImpl()
        val viewModel = LanguageViewModel.provide(this, repository)

        enableEdgeToEdge()
        setContent {
            LanguageSelectionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        LanguageSelectionScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}
