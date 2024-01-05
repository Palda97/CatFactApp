package cz.dpalecek.catfact.feature.catFact

import cz.dpalecek.catfact.core.feature.domain.FeatureEntry
import cz.dpalecek.catfact.core.navigation.domain.Screen
import cz.dpalecek.catfact.feature.catFact.di.catFactModule
import cz.dpalecek.catfact.feature.catFact.domain.CatFactDestination
import cz.dpalecek.catfact.feature.catFact.view.CatFactScreen
import org.koin.core.module.Module

class CatFactEntry : FeatureEntry<CatFactDestination>() {

    override val destinations: List<CatFactDestination> = CatFactDestination.entries

    override fun module(): Module = catFactModule()

    override fun CatFactDestination.toScreen(): Screen {
        return Screen { CatFactScreen() }
    }
}
