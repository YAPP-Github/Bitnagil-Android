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
            val propFile = rootProject.file("local.properties")
            if (propFile.exists()) {
                load(propFile.inputStream())
            }
        }

    defaultConfig {
        applicationId = "com.threegap.bitnagil"

        val kakaoNativeAppKey =
            (properties["kakao.native.app.key"] as? String)
                ?: System.getenv("KAKAO_NATIVE_APP_KEY")
                ?: throw GradleException("KAKAO_NATIVE_APP_KEY 값이 없습니다.")

        manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = kakaoNativeAppKey
        buildConfigField(
            type = "String",
            name = "KAKAO_NATIVE_APP_KEY",
            value = "\"$kakaoNativeAppKey\"",
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
