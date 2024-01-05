import org.gradle.api.JavaVersion

object AppConfig {
    const val compileSdk = 34
    const val minSdk = 26
    const val targetSdk = 34
    const val versionCode = 1
    const val versionName = "1.0"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    val javaVersion = JavaVersion.VERSION_17
    const val jvmTarget = "17"
    const val kotlinCompilerExtensionVersion = Versions.compose
}