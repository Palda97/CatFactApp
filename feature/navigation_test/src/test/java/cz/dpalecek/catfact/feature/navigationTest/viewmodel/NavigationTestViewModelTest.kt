package cz.dpalecek.catfact.feature.navigationTest.viewmodel

import cz.dpalecek.catfact.core.navigation.domain.DestinationRepository
import cz.dpalecek.catfact.core.navigation.domain.RouteDestination
import cz.dpalecek.catfact.feature.navigationTest.domain.NavigationTestDestination
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NavigationTestViewModelTest {

    private val currentDestinationFlow = MutableStateFlow<RouteDestination?>(null)
    private val destinationRepository: DestinationRepository = mockk {
        every { currentDestination } returns currentDestinationFlow
    }

    private lateinit var navigationTestViewModel: NavigationTestViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        navigationTestViewModel = NavigationTestViewModel(destinationRepository)
    }

    @Test
    fun `screenName returns empty string on null`() {
        val screenName = navigationTestViewModel.screenName.value

        assertEquals("", screenName)
    }

    @Test
    fun `screenName returns correct text`() = runTest {
        val screenNames = mutableListOf<String>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            navigationTestViewModel.screenName.toList(screenNames)
        }

        currentDestinationFlow.value = TestRoute.FIRST_ONE
        currentDestinationFlow.value = TestRoute.second_and_the_last_one

        assertEquals("First one", screenNames[1])
        assertEquals("Second and the last one", screenNames[2])
    }

    @Test
    fun `buttonStates returns all states on null`() {
        val buttonStates = navigationTestViewModel.navigateNextButtonStates.value

        assertEquals(3, buttonStates.size)
    }

    @Test
    fun `buttonStates returns states without current destination`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            navigationTestViewModel.navigateNextButtonStates.collect()
        }
        val buttonStates = mutableListOf<List<NavigationTestButtonState>>()
        fun setDestination(routeDestination: RouteDestination) {
            currentDestinationFlow.value = routeDestination
            buttonStates.add(navigationTestViewModel.navigateNextButtonStates.value)
        }

        setDestination(NavigationTestDestination.NAVIGATION_TEST_0)
        setDestination(NavigationTestDestination.NAVIGATION_TEST_1)
        setDestination(NavigationTestDestination.NAVIGATION_TEST_2)

        assertEquals(3, buttonStates.size)
        buttonStates.forEach {
            assertEquals(2, it.size)
        }
    }

    @Suppress("EnumEntryName")
    private enum class TestRoute : RouteDestination {
        FIRST_ONE,
        second_and_the_last_one,
    }
}
