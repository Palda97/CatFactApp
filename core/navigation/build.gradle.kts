plugins {
    id(Plugins.androidLibraryPlugin)
    id(Plugins.kotlinPlugin)
    id(Plugins.commonLibraryModulePlugin)
}

android {
    namespace = "cz.palda97.catfact.core.navigation"
}

dependencies {
    implementations(Dependencies.composeLibraries)
    implementation(Dependencies.composeNavigation)
}
