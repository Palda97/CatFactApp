package cz.dpalecek.catfact.feature.navigationTest.viewmodel

import androidx.lifecycle.ViewModel
import cz.dpalecek.catfact.core.domain.utils.mapStateFlow
import cz.dpalecek.catfact.core.navigation.domain.DestinationRepository
import cz.dpalecek.catfact.core.navigation.domain.RouteDestination
import cz.dpalecek.catfact.core.navigation.viewmodel.NavigationViewModel
import cz.dpalecek.catfact.core.navigation.viewmodel.NavigationViewModelImpl
import cz.dpalecek.catfact.feature.navigationTest.domain.NavigationTestDestination

internal class NavigationTestViewModel(
    destinationRepository: DestinationRepository,
) :
    NavigationViewModel by NavigationViewModelImpl(destinationRepository),
    NavigationTestActions,
    ViewModel() {

    val screenName = mapStateFlow(currentDestination) {
        it.label
    }

    val navigateNextButtonStates = mapStateFlow(currentDestination) { routeDestination ->
        val testDestination = routeDestination as? NavigationTestDestination
        allButtonStates(::navigateNext)
            .filterNot { it.first == testDestination }
            .map { it.second }
    }

    private fun allButtonStates(navigateFun: (RouteDestination) -> Unit) =
        NavigationTestDestination.values().toList().map {
            it to NavigationTestButtonState(
                label = it.label,
                action = {
                    navigateFun(it)
                },
            )
        }

    companion object {
        private val RouteDestination?.label: String
            get() = this
                ?.name
                .orEmpty()
                .lowercase()
                .replaceFirstChar { it.uppercaseChar() }
                .replace("_", " ")
    }
}

internal interface NavigationTestActions
