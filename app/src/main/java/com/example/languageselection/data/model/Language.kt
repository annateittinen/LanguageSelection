package com.example.languageselection.data.model

import androidx.annotation.StringRes

data class Language(
    val code: String,
    @StringRes val nameResId: Int
)
