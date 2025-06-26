import java.util.Properties

plugins {
    alias(libs.plugins.bitnagil.android.application)
    alias(libs.plugins.bitnagil.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.threegap.bitnagil"

    val properties =
        Properties().apply {
            load(rootProject.file("local.properties").inputStream())
        }

    defaultConfig {
        applicationId = "com.threegap.bitnagil"

        manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = properties["kakao.native.app.key"] as String
        buildConfigField(
            type = "String",
            name = "KAKAO_NATIVE_APP_KEY",
            value = "\"${properties["kakao.native.app.key"]}\"",
        )
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.datastore)
    implementation(projects.core.designsystem)
    implementation(projects.core.network)
    implementation(projects.data)
    implementation(projects.domain)
    implementation(projects.presentation)
    implementation(libs.kakao.v2.user)
}
