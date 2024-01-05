package cz.dpalecek.catfact.core.feature.domain

import cz.dpalecek.catfact.core.navigation.domain.RouteDestination
import cz.dpalecek.catfact.core.navigation.domain.Screen
import org.koin.core.module.Module

abstract class FeatureEntry<D : RouteDestination> {

    abstract val destinations: List<D>
    abstract fun module(): Module
    protected abstract fun D.toScreen(): Screen

    @Suppress("UNCHECKED_CAST")
    fun adaptScreen(destination: RouteDestination): Screen {
        return (destination as D).toScreen()
    }
}
