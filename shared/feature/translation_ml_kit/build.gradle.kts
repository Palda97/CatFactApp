plugins {
    kotlin(Plugins.multiplatformPlugin)
    id(Plugins.androidLibraryPlugin)
    id(Plugins.multiplatformModulePlugin)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    iosBaseName("translation_ml_kit")

    sourceSets {
        val commonMain by getting {
            dependencies {
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Dependencies.googleTranslationMlKit)
            }
        }
    }
}

android {
    namespace = "cz.palda97.catfact.shared.feature.translationMlKit"
}
