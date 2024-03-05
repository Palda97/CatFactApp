package cz.palda97.catfact.feature.catFact.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.palda97.shared.feature.translationMlKit.domain.Translator
import cz.palda97.catfact.core.design.utils.shareText
import cz.palda97.catfact.core.domain.utils.combineStateFlow
import cz.palda97.catfact.feature.catFact.usecase.CatFactUseCase
import cz.palda97.catfact.shared.core.domain.domain.DataAccessError
import cz.palda97.catfact.shared.core.domain.domain.DataResource
import cz.palda97.catfact.shared.feature.catFact.domain.CatFact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class CatFactViewModel(
    private val catFactUseCase: CatFactUseCase,
) :
    CatFactActions,
    ViewModel() {

    private val _catFact = MutableStateFlow<CatFact?>(null)
    val catFact = _catFact.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    private val _error = MutableStateFlow<DataAccessError?>(null)
    val error = _error.asStateFlow()

    private val _isTranslationEnabled = MutableStateFlow(false)
    val isTranslationEnabled = _isTranslationEnabled.asStateFlow()
    private val _isWifiDialogVisible = MutableStateFlow(false)
    val isWifiDialogVisible = _isWifiDialogVisible.asStateFlow()
    val translationState = combineStateFlow(
        catFactUseCase.translation,
        catFactUseCase.translatorPrerequisites
    ) { translation, prerequisites ->
        when (prerequisites) {
            is DataResource.Error,
            DataResource.Loading -> prerequisites

            else -> if (
                translation is DataResource.Error &&
                translation.error.throwable is Translator.Companion.MissingModelException
            ) {
                showWifiDialogIfNeeded()
                DataResource.Success(Unit)
            } else {
                translation
            }
        }.map {}
    }

    init {
        observeCatFact()
        onShowAnotherCatFact()
    }

    private fun showWifiDialogIfNeeded() {
        viewModelScope.launch {
            if (!catFactUseCase.areTranslatorPrerequisitesMet()) {
                _isWifiDialogVisible.value = true
            }
        }
    }

    private fun observeCatFact() {
        viewModelScope.launch {
            catFactUseCase.catFact.collect {
                when (it) {
                    is DataResource.Error -> _error.value = it.error
                    is DataResource.Success -> {
                        _catFact.value = it.data
                        if (isTranslationEnabled.value) {
                            launch { catFactUseCase.translateCurrentFact() }
                        }
                    }

                    else -> {}
                }
                _isLoading.value = it.isLoading
            }
        }
    }

    override fun onShowAnotherCatFact() {
        viewModelScope.launch {
            catFactUseCase.fetchCatFact()
        }
    }

    override fun onClearError() {
        _error.value = null
    }

    override fun onShare(context: Context, text: String) {
        context.shareText(text)
    }

    override fun onToggleTranslation(value: Boolean) {
        _isTranslationEnabled.value = value
        val fact = catFact.value
        if (value && fact != null && fact.translation == null) {
            viewModelScope.launch {
                catFactUseCase.translateCurrentFact()
            }
        }
    }

    override fun onDownloadTranslatorPrerequisites(isWifiRequired: Boolean) {
        _isWifiDialogVisible.value = false
        viewModelScope.launch {
            catFactUseCase.downloadTranslatorPrerequisites(isWifiRequired)
        }
    }

    override fun onWifiDialogClose() {
        _isWifiDialogVisible.value = false
        _isTranslationEnabled.value = false
    }
}

internal interface CatFactActions {
    fun onShowAnotherCatFact()
    fun onClearError()
    fun onShare(context: Context, text: String)
    fun onToggleTranslation(value: Boolean)
    fun onDownloadTranslatorPrerequisites(isWifiRequired: Boolean)
    fun onWifiDialogClose()
}
