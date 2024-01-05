package cz.dpalecek.catfact.core.feature

import android.app.Activity
import android.app.Application
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import cz.dpalecek.catfact.core.feature.domain.FeatureEntry
import cz.dpalecek.catfact.core.navigation.domain.DestinationRepository
import cz.dpalecek.catfact.core.navigation.domain.ScreenAdapter
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

abstract class CatFactApplication : Application() {

    abstract val features: List<FeatureEntry<*>>
    abstract val applicationModule: Module

    override fun onCreate() {
        super.onCreate()
        setUpLogger()
        setUpKoin()
    }

    private fun setUpLogger() {
        Logger.setTag(TAG)
        Logger.setMinSeverity(
            if (BuildConfig.DEBUG) Severity.Verbose else Severity.Assert
        )
    }

    private fun setUpKoin() {
        startKoin {
            modules(features.map { it.module() })
            modules(
                provideScreenAdapterModule(),
                provideNavigationModule(),
                applicationModule,
            )
        }
    }

    private fun provideScreenAdapterModule() = module {
        single { ScreenAdapter(features.map { it::adaptScreen }) }
    }

    private fun provideNavigationModule() = module {
        single { DestinationRepository() }
    }

    companion object {
        private const val TAG = "kermit"
    }
}

val Activity.destinations get() = (application as CatFactApplication).features.flatMap { it.destinations }
