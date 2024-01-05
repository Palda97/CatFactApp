package cz.dpalecek.catfact.feature.navigationTest.di

import cz.dpalecek.catfact.feature.navigationTest.viewmodel.NavigationTestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal fun navigationTestModule() = module {

    viewModel { NavigationTestViewModel(get()) }
}
