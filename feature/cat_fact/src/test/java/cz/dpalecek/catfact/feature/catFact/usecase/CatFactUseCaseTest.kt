package cz.dpalecek.catfact.feature.catFact.usecase

import cz.palda97.shared.feature.translationMlKit.domain.Translator
import cz.palda97.shared.feature.translationMlKit.domain.TranslatorOptions
import cz.palda97.shared.feature.translationMlKit.domain.TranslatorResult
import cz.dpalecek.catfact.shared.core.domain.domain.DataAccessError
import cz.dpalecek.catfact.shared.core.domain.domain.DataResource
import cz.dpalecek.catfact.shared.core.network.domain.LocaleProvider
import cz.dpalecek.catfact.shared.feature.catFact.domain.CatFact
import cz.dpalecek.catfact.shared.feature.catFact.domain.CatFactRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CatFactUseCaseTest {

    private val catFactRepository: CatFactRepository = mockk()
    private val translator: Translator = mockk()
    private val localeProvider: LocaleProvider = mockk {
        every { languageCode() } returns LANGUAGE_CODE
    }
    private val catFactUseCase = CatFactUseCase(catFactRepository, translator, localeProvider)

    @Test
    fun `fetchCatFact manages correct states of cat fact on success`() = runTest {
        coEvery { catFactRepository.fetchCatFact() } returns Result.success(CAT_FACT)
        val states = mutableListOf<DataResource<CatFact>>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            catFactUseCase.catFact.toList(states)
        }

        catFactUseCase.fetchCatFact()

        assertEquals(3, states.size)
        val iterator = states.iterator()
        assertEquals(DataResource.Empty, iterator.next())
        assertEquals(DataResource.Loading, iterator.next())
        assertEquals(DataResource.Success(CAT_FACT), iterator.next())
    }

    @Test
    fun `fetchCatFact manages correct states of cat fact on error`() = runTest {
        coEvery { catFactRepository.fetchCatFact() } returns Result.failure(EXCEPTION)
        val states = mutableListOf<DataResource<CatFact>>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            catFactUseCase.catFact.toList(states)
        }

        catFactUseCase.fetchCatFact()

        assertEquals(3, states.size)
        val iterator = states.iterator()
        assertEquals(DataResource.Empty, iterator.next())
        assertEquals(DataResource.Loading, iterator.next())
        assertEquals(DataResource.Error(EXCEPTION), iterator.next())
    }

    @Test
    fun `translateCurrentFact does nothing when there is no fact`() = runTest {
        catFactUseCase.translateCurrentFact()

        coVerify(exactly = 0) { translator.translate(any(), any(), any()) }
    }

    @Test
    fun `translateCurrentFact calls translator when there is fact`() = runTest {
        translator.translateReturns(TRANSLATOR_RESULT)
        coEvery { catFactRepository.fetchCatFact() } returns Result.success(CAT_FACT)
        catFactUseCase.fetchCatFact()

        catFactUseCase.translateCurrentFact()

        coVerify { translator.translate(CAT_FACT.fact, EN_LANGUAGE, LANGUAGE_CODE) }
    }

    private fun Translator.translateReturns(success: TranslatorResult) {
        coEvery { translate(CAT_FACT.fact, EN_LANGUAGE, LANGUAGE_CODE) } returns Result.success(
            success
        )
    }

    @Test
    fun `translateCurrentFact adds translation to cat fact with original text`() = runTest {
        translator.translateReturns(TRANSLATOR_RESULT)
        coEvery { catFactRepository.fetchCatFact() } returns Result.success(CAT_FACT)
        catFactUseCase.fetchCatFact()

        catFactUseCase.translateCurrentFact()

        assertEquals(
            TRANSLATOR_RESULT.translation,
            catFactUseCase.catFact.value.getDataOrNull()!!.translation,
        )
    }

    @Test
    fun `translateCurrentFact does no add translation to cat fact with different original text`() =
        runTest {
            translator.translateReturns(WRONG_TRANSLATOR_RESULT)
            coEvery { catFactRepository.fetchCatFact() } returns Result.success(CAT_FACT)
            catFactUseCase.fetchCatFact()

            catFactUseCase.translateCurrentFact()

            assertNull(catFactUseCase.catFact.value.getDataOrNull()!!.translation)
        }

    @Test
    fun `areTranslatorPrerequisitesMet calls translator with correct arguments`() = runTest {
        coEvery { translator.arePrerequisitesMet(EN_LANGUAGE, LANGUAGE_CODE) } returns true
        val shouldBeTrue = catFactUseCase.areTranslatorPrerequisitesMet()

        coEvery { translator.arePrerequisitesMet(EN_LANGUAGE, LANGUAGE_CODE) } returns false
        val shouldBeFalse = catFactUseCase.areTranslatorPrerequisitesMet()

        coVerify(exactly = 2) { translator.arePrerequisitesMet(EN_LANGUAGE, LANGUAGE_CODE) }
        assertTrue(shouldBeTrue)
        assertFalse(shouldBeFalse)
    }

    @Test
    fun `downloadTranslatorPrerequisites passes isWifiRequired to translator`() = runTest {
        coEvery { translator.downloadPrerequisites(any()) } returns Result.failure(EXCEPTION)
        val translatorOptions = mutableListOf<TranslatorOptions>()

        catFactUseCase.downloadTranslatorPrerequisites(true)
        catFactUseCase.downloadTranslatorPrerequisites(false)

        coVerify(exactly = 2) { translator.downloadPrerequisites(capture(translatorOptions)) }
        assertEquals(2, translatorOptions.size)
        assertTrue(translatorOptions[0].isWifiRequired)
        assertFalse(translatorOptions[1].isWifiRequired)
    }

    @Test
    fun `downloadTranslatorPrerequisites calls translateCurrentFact on success`() = runTest {
        val spyOnCatFactUseCase = spyk(catFactUseCase) {
            coEvery { translateCurrentFact() } returns Unit
        }
        coEvery { translator.downloadPrerequisites(any()) } returns Result.success(Unit)

        spyOnCatFactUseCase.downloadTranslatorPrerequisites(true)

        coVerify(exactly = 1) { spyOnCatFactUseCase.translateCurrentFact() }
    }

    @Test
    fun `downloadTranslatorPrerequisites does not call translateCurrentFact on fail`() = runTest {
        val spyOnCatFactUseCase = spyk(catFactUseCase) {
            coEvery { translateCurrentFact() } returns Unit
        }
        coEvery { translator.downloadPrerequisites(any()) } returns Result.failure(EXCEPTION)

        spyOnCatFactUseCase.downloadTranslatorPrerequisites(true)

        coVerify(exactly = 0) { spyOnCatFactUseCase.translateCurrentFact() }
    }

    @Test
    fun `downloadTranslatorPrerequisites updates translatorPrerequisites`() = runTest {
        coEvery { translator.downloadPrerequisites(any()) } returns Result.failure(EXCEPTION)

        catFactUseCase.downloadTranslatorPrerequisites(true)

        assertEquals(EXCEPTION, catFactUseCase.translatorPrerequisites.value.getErrorOrNull())
    }

    companion object {
        private val CAT_FACT = CatFact("CatFact")
        private val EXCEPTION = DataAccessError.NoConnectionError
        private const val LANGUAGE_CODE = "LANGUAGE_CODE"
        private const val EN_LANGUAGE = "en"
        private val TRANSLATOR_RESULT = TranslatorResult(CAT_FACT.fact, "translation")
        private val WRONG_TRANSLATOR_RESULT =
            TranslatorResult(CAT_FACT.fact + "not original", "translation")
    }
}
