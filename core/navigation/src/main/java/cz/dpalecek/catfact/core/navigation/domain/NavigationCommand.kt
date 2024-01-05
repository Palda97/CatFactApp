package cz.dpalecek.catfact.core.navigation.domain

sealed interface NavigationCommand {

    class Back : NavigationCommand

    data class Next(
        val routeDestination: RouteDestination,
    ) : NavigationCommand
}
