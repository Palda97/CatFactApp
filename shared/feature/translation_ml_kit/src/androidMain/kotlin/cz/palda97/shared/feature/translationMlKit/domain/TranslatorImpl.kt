package cz.palda97.shared.feature.translationMlKit.domain

import com.google.mlkit.common.MlKitException
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.TranslateRemoteModel
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class TranslatorImpl : Translator {
    override suspend fun translate(
        text: String,
        inputLanguage: String,
        outputLanguage: String,
    ): Result<TranslatorResult> {
        val options = createOptions(inputLanguage, outputLanguage)
        val translator = Translation.getClient(options)
        return suspendCoroutine { continuation ->
            translator.translate(text)
                .addOnSuccessListener {
                    continuation.resume(Result.success(TranslatorResult(text, it)))
                }
                .addOnFailureListener {
                    continuation.resume(Result.failure(it.adapted))
                }
        }
    }

    private fun createOptions(inputLanguage: String, outputLanguage: String): TranslatorOptions {
        return TranslatorOptions.Builder()
            .setSourceLanguage(inputLanguage.lowercase())
            .setTargetLanguage(outputLanguage.lowercase())
            .build()
    }

    private fun createConditions(isWifiRequired: Boolean): DownloadConditions {
        return DownloadConditions.Builder()
            .run {
                if (isWifiRequired) requireWifi() else this
            }
            .build()
    }

    private val Throwable.adapted: Throwable
        get() = if (this is MlKitException && errorCode == MlKitException.NOT_FOUND) {
            Translator.Companion.MissingModelException
        } else {
            this
        }

    override suspend fun downloadPrerequisites(
        translatorOptions: cz.palda97.shared.feature.translationMlKit.domain.TranslatorOptions,
    ): Result<Unit> {
        val options =
            createOptions(translatorOptions.inputLanguage, translatorOptions.outputLanguage)
        val translator = Translation.getClient(options)
        val conditions = createConditions(translatorOptions.isWifiRequired)
        return suspendCoroutine { continuation ->
            translator.use {
                it.downloadModelIfNeeded(conditions)
                    .addOnSuccessListener {
                        continuation.resume(Result.success(Unit))
                    }
                    .addOnFailureListener { exception ->
                        continuation.resume(Result.failure(exception))
                    }
            }
        }
    }

    override suspend fun arePrerequisitesMet(
        inputLanguage: String,
        outputLanguage: String,
    ): Boolean {
        val manager = RemoteModelManager.getInstance()
        val input = manager.isModelDownloaded(inputLanguage)
        val output = manager.isModelDownloaded(outputLanguage)
        return input && output
    }

    private suspend fun RemoteModelManager.isModelDownloaded(languageCode: String): Boolean {
        val languageModel = TranslateRemoteModel.Builder(languageCode).build()
        return suspendCoroutine { continuation ->
            isModelDownloaded(languageModel)
                .addOnSuccessListener {
                    continuation.resume(it)
                }
        }
    }
}
