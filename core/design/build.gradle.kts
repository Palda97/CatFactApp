plugins {
    id(Plugins.androidLibraryPlugin)
    id(Plugins.kotlinPlugin)
    id(Plugins.commonLibraryModulePlugin)
}

android {
    namespace = "cz.palda97.catfact.core.design"
}

dependencies {
    implementationModules(Dependencies.domainModule)
    implementations(Dependencies.composeLibraries)
}
