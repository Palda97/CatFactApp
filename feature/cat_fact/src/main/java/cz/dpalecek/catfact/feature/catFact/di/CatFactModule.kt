package cz.dpalecek.catfact.feature.catFact.di

import cz.dpalecek.catfact.feature.catFact.usecase.CatFactUseCase
import cz.dpalecek.catfact.feature.catFact.viewmodel.CatFactViewModel
import cz.dpalecek.catfact.shared.feature.catFact.dataaccess.CatFactRepositoryImpl
import cz.dpalecek.catfact.shared.feature.catFact.domain.CatFactRepository
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal fun catFactModule() = module {
    single<CatFactRepository> { CatFactRepositoryImpl(get()) }
    factoryOf(::CatFactUseCase)
    viewModelOf(::CatFactViewModel)
}
