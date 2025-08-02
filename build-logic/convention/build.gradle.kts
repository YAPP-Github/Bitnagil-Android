plugins {
    `kotlin-dsl`
}

group = "com.threegap.bitnagil.convention"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.compose.compiler.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "bitnagil.android.application"
            implementationClass = "com.threegap.bitnagil.convention.AndroidApplicationPlugin"
        }

        register("androidLibrary") {
            id = "bitnagil.android.library"
            implementationClass = "com.threegap.bitnagil.convention.AndroidLibraryPlugin"
        }

        register("androidComposeLibrary") {
            id = "bitnagil.android.compose.library"
            implementationClass = "com.threegap.bitnagil.convention.AndroidComposePlugin"
        }

        register("androidHilt") {
            id = "bitnagil.android.hilt"
            implementationClass = "com.threegap.bitnagil.convention.HiltPlugin"
        }

        register("kotlin") {
            id = "bitnagil.kotlin"
            implementationClass = "com.threegap.bitnagil.convention.KotlinJvmPlugin"
        }
    }
}
