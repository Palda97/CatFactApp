plugins {
    kotlin(Plugins.multiplatformPlugin)
    id(Plugins.androidLibraryPlugin)
    id(Plugins.multiplatformModulePlugin)
    kotlin(Plugins.serialisation)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    iosBaseName("cat_fact")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementationModules(
                    Dependencies.sharedNetworkModule,
                )
                implementation(Dependencies.jsonSerial)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "cz.dpalecek.catfact.shared.feature.catFact"
}
