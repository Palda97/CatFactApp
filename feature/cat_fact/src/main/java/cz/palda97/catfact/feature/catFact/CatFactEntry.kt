package cz.palda97.catfact.feature.catFact

import cz.palda97.catfact.core.feature.domain.FeatureEntry
import cz.palda97.catfact.core.navigation.domain.Screen
import cz.palda97.catfact.feature.catFact.di.catFactModule
import cz.palda97.catfact.feature.catFact.domain.CatFactDestination
import cz.palda97.catfact.feature.catFact.view.CatFactScreen
import org.koin.core.module.Module

class CatFactEntry : FeatureEntry<CatFactDestination>() {

    override val destinations: List<CatFactDestination> = CatFactDestination.entries

    override fun module(): Module = catFactModule()

    override fun CatFactDestination.toScreen(): Screen {
        return Screen { CatFactScreen() }
    }
}
