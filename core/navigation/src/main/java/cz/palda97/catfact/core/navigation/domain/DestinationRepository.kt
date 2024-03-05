package cz.palda97.catfact.core.navigation.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DestinationRepository {

    private val _command: MutableStateFlow<NavigationCommand?> =
        MutableStateFlow(null)
    val command = _command.asStateFlow()

    private val _currentDestination = MutableStateFlow<RouteDestination?>(null)
    val currentDestination = _currentDestination.asStateFlow()

    fun navigate(command: NavigationCommand) {
        _command.value = command
    }

    fun navigateBack() = navigate(NavigationCommand.Back())

    fun navigateNext(destination: RouteDestination) = navigate(NavigationCommand.Next(destination))

    fun clearCommand() {
        _command.value = null
    }

    fun setCurrentDestination(destination: RouteDestination) {
        _currentDestination.value = destination
    }
}
