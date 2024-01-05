import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

class CommonApplicationModulePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.android<BaseAppModuleExtension> {
            applicationDefaultConfig()
            buildTypes()
            lint()
            javaVersion()
            compose()
            packaging()
        }
        target.addKermitDependency()
    }
}

private fun BaseAppModuleExtension.applicationDefaultConfig() {
    compileSdk = AppConfig.compileSdk
    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        testInstrumentationRunner = AppConfig.testInstrumentationRunner
        vectorDrawables {
            useSupportLibrary = true
        }
    }
}
