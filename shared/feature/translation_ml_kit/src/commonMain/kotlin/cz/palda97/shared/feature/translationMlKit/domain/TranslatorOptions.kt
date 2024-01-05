package cz.palda97.shared.feature.translationMlKit.domain

data class TranslatorOptions(
    val isWifiRequired: Boolean,
    val inputLanguage: String,
    val outputLanguage: String
)
