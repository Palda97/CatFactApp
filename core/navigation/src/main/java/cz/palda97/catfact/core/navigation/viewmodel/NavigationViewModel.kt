package cz.palda97.catfact.core.navigation.viewmodel

import cz.palda97.catfact.core.navigation.domain.DestinationRepository
import cz.palda97.catfact.core.navigation.domain.RouteDestination

interface NavigationViewModel : BackNavigation {

    val destinationRepository: DestinationRepository

    val currentDestination get() = destinationRepository.currentDestination

    fun navigateNext(route: RouteDestination) = destinationRepository.navigateNext(route)

    override fun navigateBack() = destinationRepository.navigateBack()
}

interface BackNavigation {
    fun navigateBack()
}

class NavigationViewModelImpl(override val destinationRepository: DestinationRepository) :
    NavigationViewModel
