package cz.palda97.catfact.feature.catFact.di

import cz.palda97.catfact.feature.catFact.usecase.CatFactUseCase
import cz.palda97.catfact.feature.catFact.viewmodel.CatFactViewModel
import cz.palda97.catfact.shared.feature.catFact.dataaccess.CatFactRepositoryImpl
import cz.palda97.catfact.shared.feature.catFact.domain.CatFactRepository
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal fun catFactModule() = module {
    single<CatFactRepository> { CatFactRepositoryImpl(get()) }
    factoryOf(::CatFactUseCase)
    viewModelOf(::CatFactViewModel)
}
