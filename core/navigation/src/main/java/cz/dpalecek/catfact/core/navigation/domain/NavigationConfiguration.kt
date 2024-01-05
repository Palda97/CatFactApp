package cz.dpalecek.catfact.core.navigation.domain

data class NavigationConfiguration(
    val initialDestination: RouteDestination,
    val destinations: List<RouteDestination>,
    val screenAdapter: ScreenAdapter,
    val repository: DestinationRepository,
    val onEmptyStack: () -> Unit,
)
