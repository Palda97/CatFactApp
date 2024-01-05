plugins {
    id(Plugins.androidLibraryPlugin)
    id(Plugins.kotlinPlugin)
    id(Plugins.commonLibraryModulePlugin)
}

android {
    namespace = "cz.dpalecek.catfact.core.design"
}

dependencies {
    implementationModules(Dependencies.domainModule)
    implementations(Dependencies.composeLibraries)
}
