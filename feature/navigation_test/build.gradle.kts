plugins {
    id(Plugins.androidLibraryPlugin)
    id(Plugins.kotlinPlugin)
    id(Plugins.commonLibraryModulePlugin)
    kotlin(Plugins.serialisation)
}

android {
    namespace = "cz.dpalecek.catfact.feature.navigationTest"
}

dependencies {
    implementationModules(
        Dependencies.designModule,
        Dependencies.featureModule,
        Dependencies.navigationModule,
        Dependencies.domainModule,
    )
    implementations(Dependencies.composeLibraries)
    implementations(Dependencies.koinLibraries)
    implementation(Dependencies.jsonSerial)
    testImplementations(Dependencies.testLibraries)
}
