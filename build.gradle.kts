import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_SRC_DIR_JAVA
import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_SRC_DIR_KOTLIN
import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_TEST_SRC_DIR_JAVA
import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_TEST_SRC_DIR_KOTLIN

plugins {
    id(Plugins.detektPlugin) version(Versions.detekt)
    id(Plugins.gradleVersionsPlugin) version(Versions.gradleVersionsPlugin)
    kotlin(Plugins.serialisation) version(Versions.kotlin)
}

setUpDetect()
configureDependencyCheck()

fun setUpDetect() {
    allprojects {
        apply(plugin = Plugins.detektPlugin)
        dependencies {
            detektPlugins(Dependencies.detektFormatting)
        }
        detekt {
            source.from(
                    DEFAULT_SRC_DIR_JAVA,
                    DEFAULT_TEST_SRC_DIR_JAVA,
                    DEFAULT_SRC_DIR_KOTLIN,
                    DEFAULT_TEST_SRC_DIR_KOTLIN,
                    "src/commonMain/kotlin",
                    "src/commonTest/kotlin",
                    "src/androidTest/kotlin"
                )
            config.from(files("$rootDir/config/detekt/detekt.yml"))
        }
    }
}

// https://github.com/ben-manes/gradle-versions-plugin
// run gradle dependencyUpdates
fun configureDependencyCheck() {
    tasks.withType<DependencyUpdatesTask> {
        rejectVersionIf {
            isNonStable(candidate.version) && !isNonStable(currentVersion)
        }
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}
