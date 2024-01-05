package cz.dpalecek.catfact.feature.catFact.usecase

import cz.palda97.shared.feature.translationMlKit.domain.Translator
import cz.palda97.shared.feature.translationMlKit.domain.TranslatorOptions
import cz.palda97.shared.feature.translationMlKit.domain.TranslatorResult
import cz.dpalecek.catfact.core.domain.utils.fetch
import cz.dpalecek.catfact.shared.core.domain.domain.emptyDataStateFlow
import cz.dpalecek.catfact.shared.core.network.domain.LocaleProvider
import cz.dpalecek.catfact.shared.feature.catFact.domain.CatFact
import cz.dpalecek.catfact.shared.feature.catFact.domain.CatFactRepository
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class CatFactUseCase(
    private val catFactRepository: CatFactRepository,
    private val translator: Translator,
    private val localeProvider: LocaleProvider,
) {

    private val _catFact = emptyDataStateFlow<CatFact>()
    val catFact = _catFact.asStateFlow()

    private val _translation = emptyDataStateFlow<TranslatorResult>()
    val translation = _translation.asStateFlow()

    private val _translatorPrerequisites = emptyDataStateFlow<Unit>()
    val translatorPrerequisites = _translatorPrerequisites.asStateFlow()

    suspend fun fetchCatFact() {
        fetch(_catFact) {
            catFactRepository.fetchCatFact()
        }
    }

    suspend fun translateCurrentFact() {
        catFact.value.getDataOrNull()?.fact?.let { text ->
            fetch(
                flow = _translation,
                onSuccess = ::addTranslationToCatFact,
            ) {
                translator.translate(
                    text,
                    EN_LANGUAGE,
                    localeProvider.languageCode(),
                )
            }
        }
    }

    private fun addTranslationToCatFact(translatorResult: TranslatorResult) {
        _catFact.update { dataResource ->
            dataResource.map {
                if (it.fact == translatorResult.original) {
                    it.copy(translation = translatorResult.translation)
                } else {
                    it
                }
            }
        }
    }

    suspend fun areTranslatorPrerequisitesMet(): Boolean {
        return translator.arePrerequisitesMet(EN_LANGUAGE, localeProvider.languageCode())
    }

    suspend fun downloadTranslatorPrerequisites(isWifiRequired: Boolean) {
        fetch(
            flow = _translatorPrerequisites,
            onSuccess = { translateCurrentFact() }
        ) {
            translator.downloadPrerequisites(
                TranslatorOptions(
                    isWifiRequired = isWifiRequired,
                    inputLanguage = EN_LANGUAGE,
                    outputLanguage = localeProvider.languageCode(),
                )
            )
        }
    }
}

private const val EN_LANGUAGE = "en"
