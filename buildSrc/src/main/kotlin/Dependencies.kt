import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

object Dependencies {

    // Detekt
    const val detektFormatting = "io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.detekt}"

    // UI
    private const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private const val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
    private const val composeFoundation =
        "androidx.compose.foundation:foundation:${Versions.compose}"
    private const val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
    private const val composePreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    private const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"

    val composeLibraries = listOf(
        coreKtx,
        composeUi,
        composeFoundation,
        composeMaterial,
        composePreview,
        composeUiTooling,
        activityCompose,
    )

    // Navigation
    const val composeNavigation = "androidx.navigation:navigation-compose:${Versions.navigation}"

    // Lifecycle
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"

    // Coroutines
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"

    // Logging
    const val kermit = "co.touchlab:kermit:${Versions.kermit}"

    // Koin
    private const val koinCore = "io.insert-koin:koin-core:${Versions.koin}"
    private const val koinAndroid = "io.insert-koin:koin-android:${Versions.koin}"
    private const val koinCompose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"

    val koinLibraries = listOf(
        koinCore,
        koinAndroid,
        koinCompose,
    )

    // Ktor
    const val ktor = "io.ktor:ktor-client-core:${Versions.ktor}"

    const val ktorAndroidEngine = "io.ktor:ktor-client-okhttp:${Versions.ktor}"
    const val ktorIOSEngine = "io.ktor:ktor-client-darwin:${Versions.ktor}"

    private const val ktorLogging = "io.ktor:ktor-client-logging:${Versions.ktor}"
    private const val ktorContent = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
    private const val ktorSerialization = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
    private const val ktorAuth = "io.ktor:ktor-client-auth:${Versions.ktor}"
    private const val ktorMock = "io.ktor:ktor-client-mock:${Versions.ktor}"

    val ktorPlugins = listOf(
        ktorLogging,
        ktorContent,
        ktorSerialization,
        ktorAuth,
        ktorMock,
    )

    // Serialization
    const val jsonSerial =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}"

    // Translation
    const val googleTranslationMlKit = "com.google.mlkit:translate:${Versions.googleMlKit}"

    // Test
    private const val junit = "junit:junit:${Versions.junit}"
    private const val mockk = "io.mockk:mockk:${Versions.mockk}"
    private const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"

    val testLibraries = listOf(
        junit,
        mockk,
        coroutinesTest,
    )

    // Core modules
    const val designModule = ":core:design"
    const val featureModule = ":core:feature"
    const val domainModule = ":core:domain"
    const val navigationModule = ":core:navigation"

    // Feature modules
    const val navigationTestModule = ":feature:navigation_test"
    const val catFactModule = ":feature:cat_fact"

    // Shared

    // Core modules
    const val sharedDomainModule = ":shared:core:domain"
    const val sharedNetworkModule = ":shared:core:network"

    // Feature modules
    const val sharedCatFactModule = ":shared:feature:cat_fact"
    const val sharedTranslationMlKit = ":shared:feature:translation_ml_kit"
}

fun DependencyHandlerScope.implementations(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun KotlinDependencyHandler.implementations(list: List<String>) {
    list.forEach { dependency ->
        implementation(dependency)
    }
}

fun DependencyHandler.testImplementations(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}

fun DependencyHandlerScope.implementationModules(vararg name: String) {
    name.forEach {
        add("implementation", project(mapOf("path" to it)))
    }
}

fun KotlinDependencyHandler.implementationModules(vararg name: String) {
    name.forEach { dependency ->
        implementation(project(mapOf("path" to dependency)))
    }
}

fun DependencyHandlerScope.apiModules(vararg name: String) {
    name.forEach {
        add("api", project(mapOf("path" to it)))
    }
}

fun KotlinDependencyHandler.apiModules(vararg name: String) {
    name.forEach {
        api(project(mapOf("path" to it)))
    }
}
