plugins {
    id(Plugins.androidLibraryPlugin)
    id(Plugins.kotlinPlugin)
    id(Plugins.commonLibraryModulePlugin)
}

android {
    namespace = "cz.palda97.catfact.core.feature"
}

dependencies {
    implementations(Dependencies.composeLibraries)
    implementations(Dependencies.koinLibraries)
    implementationModules(Dependencies.navigationModule)
}
