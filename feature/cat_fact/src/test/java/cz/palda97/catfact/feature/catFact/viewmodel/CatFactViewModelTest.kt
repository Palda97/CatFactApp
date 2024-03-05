package cz.palda97.catfact.feature.catFact.viewmodel

import cz.palda97.shared.feature.translationMlKit.domain.Translator
import cz.palda97.shared.feature.translationMlKit.domain.TranslatorResult
import cz.palda97.catfact.feature.catFact.usecase.CatFactUseCase
import cz.palda97.catfact.shared.core.domain.domain.DataAccessError
import cz.palda97.catfact.shared.core.domain.domain.DataResource
import cz.palda97.catfact.shared.core.domain.domain.asDataResource
import cz.palda97.catfact.shared.core.domain.domain.emptyDataStateFlow
import cz.palda97.catfact.shared.feature.catFact.domain.CatFact
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CatFactViewModelTest {

    private val catFactFlow = emptyDataStateFlow<CatFact>()
    private val translationFlow = emptyDataStateFlow<TranslatorResult>()
    private val translatorPrerequisitesFlow = emptyDataStateFlow<Unit>()
    private val catFactUseCase: CatFactUseCase = mockk(relaxUnitFun = true) {
        every { catFact } returns catFactFlow
        every { translation } returns translationFlow
        every { translatorPrerequisites } returns translatorPrerequisitesFlow
    }
    private lateinit var catFactViewModel: CatFactViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        catFactViewModel = CatFactViewModel(catFactUseCase)
    }

    @Test
    fun `fetch cat fact on init`() {
        coVerify(exactly = 1) { catFactUseCase.fetchCatFact() }
    }

    @Test
    fun `isLoading is mirroring loading state of cat fact flow`() = runTest {
        val states = mutableListOf<Boolean>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            catFactViewModel.isLoading.toList(states)
        }

        catFactFlow.value = DataResource.Loading

        catFactFlow.value = DataResource.Error(EXCEPTION)
        catFactFlow.value = DataResource.Success(CAT_FACT)
        catFactFlow.value = DataResource.Loading

        catFactFlow.value = DataResource.Success(CAT_FACT)
        catFactFlow.value = DataResource.Loading

        assertEquals(6, states.size)
        val iterator = states.iterator()
        repeat(3) {
            assertEquals(false, iterator.next())
            assertEquals(true, iterator.next())
        }
    }

    @Test
    fun `error starts empty`() {
        assertEquals(null, catFactViewModel.error.value)
    }

    @Test
    fun `error stores exception on fetching error`() {
        catFactFlow.value = DataResource.Error(EXCEPTION)

        assertEquals(EXCEPTION, catFactViewModel.error.value)
    }

    @Test
    fun `error is cleared after onClearError`() {
        catFactFlow.value = DataResource.Error(EXCEPTION)

        catFactViewModel.onClearError()

        assertEquals(null, catFactViewModel.error.value)
    }

    @Test
    fun `cat fact stores successful data from usecase`() = runTest {
        val states = mutableListOf<CatFact?>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            catFactViewModel.catFact.toList(states)
        }

        catFactFlow.value = CAT_FACT.asDataResource()
        catFactFlow.value = CAT_FACT_2.asDataResource()

        assertEquals(3, states.size)
        val iterator = states.iterator()
        assertEquals(null, iterator.next())
        assertEquals(CAT_FACT, iterator.next())
        assertEquals(CAT_FACT_2, iterator.next())
    }

    @Test
    fun `cat fact does not updates on fail`() {
        catFactFlow.value = CAT_FACT.asDataResource()

        catFactFlow.value = DataResource.Error(EXCEPTION)

        assertEquals(CAT_FACT, catFactViewModel.catFact.value)
    }

    @Test
    fun `do not translate when isTranslationEnabled is false`() {
        catFactFlow.value = CAT_FACT.asDataResource()

        coVerify(exactly = 0) { catFactUseCase.translateCurrentFact() }
    }

    @Test
    fun `translate when isTranslationEnabled is true`() {
        catFactViewModel.onToggleTranslation(true)

        catFactFlow.value = CAT_FACT.asDataResource()

        coVerify(exactly = 1) { catFactUseCase.translateCurrentFact() }
    }

    @Test
    fun `onToggleTranslation calls translateCurrentFact if translation is missing and can be obtained`() {
        catFactFlow.value = CAT_FACT.asDataResource()

        catFactViewModel.onToggleTranslation(true)

        coVerify(exactly = 1) { catFactUseCase.translateCurrentFact() }
    }

    @Test
    fun `onToggleTranslation does no call translateCurrentFact on false`() {
        catFactFlow.value = CAT_FACT.asDataResource()

        catFactViewModel.onToggleTranslation(false)

        coVerify(exactly = 0) { catFactUseCase.translateCurrentFact() }
    }

    @Test
    fun `onToggleTranslation does no call translateCurrentFact when translation is present`() {
        catFactFlow.value = CAT_FACT.copy(translation = "").asDataResource()

        catFactViewModel.onToggleTranslation(true)

        coVerify(exactly = 0) { catFactUseCase.translateCurrentFact() }
    }

    @Test
    fun `do not show wifi dialog when prerequisites are met`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            catFactViewModel.translationState.collect()
        }
        coEvery { catFactUseCase.areTranslatorPrerequisitesMet() } returns true

        translationFlow.value = MISSING_MODEL_DATA_RESOURCE

        assertFalse(catFactViewModel.isWifiDialogVisible.value)
    }

    @Test
    fun `show wifi dialog when prerequisites are not met`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            catFactViewModel.translationState.collect()
        }
        coEvery { catFactUseCase.areTranslatorPrerequisitesMet() } returns false

        translationFlow.value = MISSING_MODEL_DATA_RESOURCE

        assertTrue(catFactViewModel.isWifiDialogVisible.value)
    }

    @Test
    fun `onDownloadTranslatorPrerequisites turns off wifi dialog`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            catFactViewModel.translationState.collect()
        }
        coEvery { catFactUseCase.areTranslatorPrerequisitesMet() } returns false
        translationFlow.value = MISSING_MODEL_DATA_RESOURCE

        catFactViewModel.onDownloadTranslatorPrerequisites(true)

        assertFalse(catFactViewModel.isWifiDialogVisible.value)
    }

    @Test
    fun `onDownloadTranslatorPrerequisites calls usecase with correct parameters`() {
        val isWifiRequired = mutableListOf<Boolean>()

        catFactViewModel.onDownloadTranslatorPrerequisites(true)
        catFactViewModel.onDownloadTranslatorPrerequisites(false)

        coVerify(exactly = 2) { catFactUseCase.downloadTranslatorPrerequisites(capture(isWifiRequired)) }
        assertTrue(isWifiRequired[0])
        assertFalse(isWifiRequired[1])
    }

    companion object {
        val CAT_FACT = CatFact("CAT_FACT")
        val CAT_FACT_2 = CatFact("CAT_FACT_2")
        val EXCEPTION = DataAccessError.NoConnectionError
        val MISSING_MODEL_DATA_RESOURCE =
            DataResource.Error(DataAccessError.GeneralError(Translator.Companion.MissingModelException))
    }
}
