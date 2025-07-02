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

    buildTypes {
        debug {
            val devUrl = properties["bitnagil.dev.url"] as? String ?: "https://dev.example.com"
            buildConfigField("String", "BASE_URL", "\"$devUrl\"")
        }

        release {
            val prodUrl = properties["bitnagil.prod.url"] as? String ?: "https://prod.example.com"
            buildConfigField("String", "BASE_URL", "\"$prodUrl\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.datastore)
    implementation(projects.core.designsystem)
    implementation(projects.core.network)
    implementation(projects.core.security)
    implementation(projects.data)
    implementation(projects.domain)
    implementation(projects.presentation)

    implementation(libs.kakao.v2.user)
    implementation(platform(libs.retrofit.bom))
    implementation(libs.bundles.retrofit)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.okhttp)
}
