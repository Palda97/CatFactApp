package cz.dpalecek.catfact.core.navigation.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.dpalecek.catfact.core.navigation.domain.DestinationRepository
import cz.dpalecek.catfact.core.navigation.domain.NavigationCommand
import cz.dpalecek.catfact.core.navigation.domain.NavigationConfiguration

@Composable
fun NavHostCAT(
    configuration: NavigationConfiguration,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = configuration.initialDestination.routeName()
    ) {
        configuration.destinations.forEach { routeDestination ->
            composable(routeDestination.routeName()) {
                LaunchedEffect(key1 = Unit) { configuration.repository.setCurrentDestination(routeDestination) }
                configuration.screenAdapter.adapt(routeDestination).content()
            }
        }
    }
    val command by configuration.repository.command.collectAsState()
    LaunchedEffect(key1 = command) {
        command?.let {
            navigate(
                command = it,
                navController = navController,
                onEmptyStack = configuration.onEmptyStack,
                repository = configuration.repository,
            )
        }
    }
}

private fun navigate(
    command: NavigationCommand,
    navController: NavController,
    onEmptyStack: () -> Unit,
    repository: DestinationRepository,
) {
    when (command) {
        is NavigationCommand.Back -> {
            if (!navController.navigateUp()) {
                onEmptyStack()
            }
        }

        is NavigationCommand.Next -> navController.navigate(command.routeDestination.routeName())
    }
    repository.clearCommand()
}
