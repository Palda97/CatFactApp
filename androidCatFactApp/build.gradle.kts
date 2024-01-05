plugins {
    id(Plugins.androidApplicationPlugin)
    id(Plugins.kotlinPlugin)
    id(Plugins.commonApplicationModulePlugin)
}

android {
    namespace = "cz.dpalecek.catfact.androidCatFactApp"

    defaultConfig {
        applicationId = "cz.dpalecek.catfact.androidCatFactApp"
    }

    val releasePasswordKey = "storePassword"
    val releasePassword = if (extra.has(releasePasswordKey)) {
        extra[releasePasswordKey].toString()
    } else ""

    signingConfigs {
        create("release") {
            keyAlias = "key0"
            keyPassword = releasePassword
            storeFile = file("keystore.jks")
            storePassword = releasePassword
        }
    }

    buildTypes {
        debug {
            val debugSuffix = ".debug"
            applicationIdSuffix = debugSuffix
            versionNameSuffix = debugSuffix
        }
        release {
            val isSigningEnabled = System.getenv("DO_NOT_SIGN_THE_APP").toBoolean().not()
            if (isSigningEnabled) {
                signingConfig = signingConfigs.getByName("release")
            }
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementationModules(
        Dependencies.designModule,
        Dependencies.featureModule,
        Dependencies.navigationModule,
        Dependencies.sharedNetworkModule,
    )
    implementationModules(
        Dependencies.catFactModule,
        Dependencies.sharedTranslationMlKit,
    )
    implementations(Dependencies.koinLibraries)
    implementation(Dependencies.activityCompose)
}