plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("common-application-module-plugin") {
            id = "common-application-module-plugin"
            implementationClass = "CommonApplicationModulePlugin"
        }
        register("common-library-module-plugin") {
            id = "common-library-module-plugin"
            implementationClass = "CommonLibraryModulePlugin"
        }
        register("common-core-library-module-plugin") {
            id = "common-core-library-module-plugin"
            implementationClass = "CommonCoreLibraryModulePlugin"
        }
        register("multiplatform-module-plugin") {
            id = "multiplatform-module-plugin"
            implementationClass = "MultiplatformModulePlugin"
        }
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    compileOnly(gradleApi())
    implementation("com.android.tools.build:gradle:8.1.1")
    implementation(kotlin("gradle-plugin", "1.9.20"))
}
