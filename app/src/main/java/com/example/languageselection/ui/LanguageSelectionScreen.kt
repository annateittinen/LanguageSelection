package com.example.languageselection.ui

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.languageselection.R
import com.example.languageselection.data.model.Language
import com.example.languageselection.ui.viewmodel.LanguageViewModel

@Composable
fun LanguageSelectionScreen(viewModel: LanguageViewModel) {
    val languages by viewModel.languages.collectAsState()
    val currentLocales = AppCompatDelegate.getApplicationLocales()
    val currentLanguageCode = if (!currentLocales.isEmpty) currentLocales.get(0)?.language else "en"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.greeting),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(languages) { language ->
                LanguageItem(
                    language = language,
                    isSelected = language.code == currentLanguageCode
                ) {
                    viewModel.selectLanguage(language)
                }
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun LanguageItem(language: Language, isSelected: Boolean, onClick: () -> Unit) {
    Text(
        text = stringResource(language.nameResId),
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isSelected) Color.LightGray else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(16.dp),
        style = MaterialTheme.typography.bodyLarge
    )
}
