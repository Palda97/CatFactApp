package cz.dpalecek.catfact.androidCatFactApp.di

import cz.palda97.shared.feature.translationMlKit.domain.Translator
import cz.palda97.shared.feature.translationMlKit.domain.TranslatorImpl
import cz.dpalecek.catfact.shared.core.network.dataaccess.NetworkClient
import cz.dpalecek.catfact.shared.core.network.dataaccess.NetworkConfiguration
import cz.dpalecek.catfact.shared.core.network.domain.LocaleProvider
import org.koin.dsl.module

internal val ApplicationModule = module {
    single {
        LocaleProvider()
    }
    single {
        NetworkClient(
            configuration = NetworkConfiguration(
                host = CAT_FACTS,
                localeProvider = get(),
            )
        )
    }
    single<Translator> {
        TranslatorImpl()
    }
}

private const val CAT_FACTS = "catfact.ninja"
