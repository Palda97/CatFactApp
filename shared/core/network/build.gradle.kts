plugins {
    kotlin(Plugins.multiplatformPlugin)
    id(Plugins.androidLibraryPlugin)
    id(Plugins.multiplatformModulePlugin)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    iosBaseName("network")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementationModules(Dependencies.sharedDomainModule)
                api(Dependencies.ktor)
                implementations(Dependencies.ktorPlugins)
                implementation(Dependencies.jsonSerial)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Dependencies.ktorAndroidEngine)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(Dependencies.ktorIOSEngine)
            }
        }
    }
}

android {
    namespace = "cz.palda97.catfact.shared.core.network"
}
