import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MultiplatformModulePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.kotlin {
            applyDefaultHierarchyTemplate()
            androidTarget {
                compilations.all {
                    kotlinOptions {
                        jvmTarget = AppConfig.jvmTarget
                    }
                }
            }
            sourceSets.commonMain.configure {
                dependencies {
                    implementation(Dependencies.kermit)
                }
            }
        }

        target.android<LibraryExtension> {
            compileSdk = AppConfig.compileSdk
            defaultConfig {
                minSdk = AppConfig.minSdk
            }
            lint()
            compileOptions {
                sourceCompatibility = AppConfig.javaVersion
                targetCompatibility = AppConfig.javaVersion
            }
        }
    }
}

fun KotlinMultiplatformExtension.iosBaseName(baseName: String) {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            this.baseName = baseName
        }
    }
}
