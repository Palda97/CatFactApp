import org.gradle.api.JavaVersion

object AppConfig {
    const val compileSdk = 35
    const val minSdk = 26
    const val targetSdk = 35
    const val versionCode = 4
    const val versionName = "1.2.1"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    val javaVersion = JavaVersion.VERSION_17
    const val jvmTarget = "17"
    const val kotlinCompilerExtensionVersion = Versions.compose
}
