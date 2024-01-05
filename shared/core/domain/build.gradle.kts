plugins {
    kotlin(Plugins.multiplatformPlugin)
    id(Plugins.androidLibraryPlugin)
    id(Plugins.multiplatformModulePlugin)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    iosBaseName("domain")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.coroutinesCore)
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
    namespace = "cz.dpalecek.catfact.shared.core.domain"
}
