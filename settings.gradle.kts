pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CatFactApp"
include(":androidCatFactApp")
include(":core:design")
include(":core:feature")
include(":core:domain")
include(":core:navigation")
include(":feature:navigation_test")
include(":shared:core:domain")
include(":shared:core:network")
include(":shared:feature:cat_fact")
include(":feature:cat_fact")
include(":shared:feature:translation_ml_kit")
