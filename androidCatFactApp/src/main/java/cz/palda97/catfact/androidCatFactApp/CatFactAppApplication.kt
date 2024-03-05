package cz.palda97.catfact.androidCatFactApp

import cz.palda97.catfact.androidCatFactApp.di.ApplicationModule
import cz.palda97.catfact.core.feature.CatFactApplication
import cz.palda97.catfact.core.feature.domain.FeatureEntry
import cz.palda97.catfact.feature.catFact.CatFactEntry
import org.koin.core.module.Module

class CatFactAppApplication : CatFactApplication() {
    override val features: List<FeatureEntry<*>> = listOf(
        CatFactEntry(),
    )
    override val applicationModule: Module = ApplicationModule
}
