plugins {
    id(Plugins.androidLibraryPlugin)
    id(Plugins.kotlinPlugin)
    id(Plugins.commonCoreLibraryModulePlugin)
}

android {
    namespace = "cz.palda97.catfact.core.domain"
}

dependencies {
    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.viewModel)
    apiModules(Dependencies.sharedDomainModule)
}
