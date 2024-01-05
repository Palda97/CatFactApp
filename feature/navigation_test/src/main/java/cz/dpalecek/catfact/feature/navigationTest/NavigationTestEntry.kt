package cz.dpalecek.catfact.feature.navigationTest

import cz.dpalecek.catfact.core.feature.domain.FeatureEntry
import cz.dpalecek.catfact.core.navigation.domain.Screen
import cz.dpalecek.catfact.feature.navigationTest.di.navigationTestModule
import cz.dpalecek.catfact.feature.navigationTest.domain.NavigationTestDestination
import cz.dpalecek.catfact.feature.navigationTest.view.NavigationTestScreen

class NavigationTestEntry : FeatureEntry<NavigationTestDestination>() {

    override val destinations = NavigationTestDestination.values().toList()

    override fun module() = navigationTestModule()

    override fun NavigationTestDestination.toScreen() = Screen {
        NavigationTestScreen()
    }
}
