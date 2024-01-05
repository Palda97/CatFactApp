import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun LibraryExtension.commonCoreLibraryAndroidBlock() {
    defaultConfig()
    buildTypes()
    lint()
    javaVersion()
    packaging()
}

private fun LibraryExtension.defaultConfig() {
    compileSdk = AppConfig.compileSdk
    defaultConfig {
        minSdk = AppConfig.minSdk
        testInstrumentationRunner = AppConfig.testInstrumentationRunner
        consumerProguardFiles("consumer-rules.pro")
    }
}

internal fun Extension.buildTypes() {
    buildTypes {
        findByName("release")!!.apply {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

internal typealias Extension = CommonExtension<*, *, *, *, *>

internal fun Extension.lint() {
    lint {
        warningsAsErrors = true
    }
}

internal fun Extension.javaVersion() {
    compileOptions {
        sourceCompatibility = AppConfig.javaVersion
        targetCompatibility = AppConfig.javaVersion
    }
    kotlinOptions {
        jvmTarget = AppConfig.jvmTarget
    }
}

internal fun Extension.packaging() {
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

internal inline fun <reified T : Extension> Project.android(androidBlock: T.() -> Unit) {
    withExtension("android", androidBlock)
}

private inline fun <reified T : Extension> Project.withExtension(
    extensionName: String,
    block: T.() -> Unit
) {
    val androidExtensions = extensions.getByName(extensionName)
    if (androidExtensions is T) {
        androidExtensions.block()
    }
}

internal fun Project.kotlin(kotlinBlock: KotlinMultiplatformExtension.() -> Unit) {
    withExtension("kotlin", kotlinBlock)
}

internal fun Extension.compose() {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = AppConfig.kotlinCompilerExtensionVersion
    }
}

private fun Extension.kotlinOptions(configure: Action<KotlinJvmOptions>): Unit =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("kotlinOptions", configure)

internal fun Project.addKermitDependency() {
    dependencies {
        add("implementation", Dependencies.kermit)
    }
}
