plugins {
    id(Plugins.androidLibraryPlugin)
    id(Plugins.kotlinPlugin)
    id(Plugins.commonCoreLibraryModulePlugin)
}

android {
    namespace = "cz.dpalecek.catfact.core.domain"
}

dependencies {
    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.viewModel)
    apiModules(Dependencies.sharedDomainModule)
}
