package cz.palda97.shared.feature.translationMlKit.domain

actual class TranslatorImpl : Translator {
    override suspend fun translate(
        text: String,
        inputLanguage: String,
        outputLanguage: String,
    ): Result<TranslatorResult> {
        TODO("Not yet implemented")
    }

    override suspend fun downloadPrerequisites(translatorOptions: TranslatorOptions): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun arePrerequisitesMet(
        inputLanguage: String,
        outputLanguage: String
    ): Boolean {
        TODO("Not yet implemented")
    }
}
