plugins {
    id(Plugins.androidLibraryPlugin)
    id(Plugins.kotlinPlugin)
    id(Plugins.commonLibraryModulePlugin)
    kotlin(Plugins.serialisation)
}

android {
    namespace = "cz.palda97.catfact.feature.catFact"
}

dependencies {
    implementationModules(
        Dependencies.designModule,
        Dependencies.featureModule,
        Dependencies.navigationModule,
        Dependencies.sharedNetworkModule,
        Dependencies.domainModule,
        Dependencies.sharedCatFactModule,
        Dependencies.sharedTranslationMlKit,
    )
    implementations(Dependencies.composeLibraries)
    implementations(Dependencies.koinLibraries)
    implementation(Dependencies.jsonSerial)
    testImplementations(Dependencies.testLibraries)
}
