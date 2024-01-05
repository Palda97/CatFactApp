package cz.palda97.shared.feature.translationMlKit.domain

interface Translator {
    suspend fun translate(
        text: String,
        inputLanguage: String,
        outputLanguage: String,
    ): Result<TranslatorResult>

    suspend fun downloadPrerequisites(
        translatorOptions: TranslatorOptions,
    ): Result<Unit>

    suspend fun arePrerequisitesMet(
        inputLanguage: String,
        outputLanguage: String,
    ): Boolean

    companion object {
        object MissingModelException : Throwable(cause = null)
    }
}
