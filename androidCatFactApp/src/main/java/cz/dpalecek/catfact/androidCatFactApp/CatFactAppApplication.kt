package cz.dpalecek.catfact.androidCatFactApp

import cz.dpalecek.catfact.androidCatFactApp.di.ApplicationModule
import cz.dpalecek.catfact.core.feature.CatFactApplication
import cz.dpalecek.catfact.core.feature.domain.FeatureEntry
import cz.dpalecek.catfact.feature.catFact.CatFactEntry
import org.koin.core.module.Module

class CatFactAppApplication : CatFactApplication() {
    override val features: List<FeatureEntry<*>> = listOf(
        CatFactEntry(),
    )
    override val applicationModule: Module = ApplicationModule
}
