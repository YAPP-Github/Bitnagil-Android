import com.threegap.bitnagil.convention.extension.propertyOrNull
import com.threegap.bitnagil.convention.extension.requireProperty

plugins {
    alias(libs.plugins.bitnagil.android.application)
    alias(libs.plugins.bitnagil.android.hilt)
    alias(libs.plugins.bitnagil.kotlin.serialization)
}

android {
    signingConfigs {
        create("release") {
            keyAlias = requireProperty("release.key.alias", "RELEASE_KEY_ALIAS")
            keyPassword = requireProperty("release.key.password", "RELEASE_KEY_PASSWORD")
            storePassword = requireProperty("release.keystore.password", "RELEASE_KEYSTORE_PASSWORD")
            storeFile = File("${propertyOrNull("release.keystore.path", "RELEASE_KEYSTORE_PATH")}")
        }
    }

    defaultConfig {
        val kakaoNativeAppKey = requireProperty("kakao.native.app.key", "KAKAO_NATIVE_APP_KEY")

        manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = kakaoNativeAppKey
        buildConfigField(
            type = "String",
            name = "KAKAO_NATIVE_APP_KEY",
            value = "\"$kakaoNativeAppKey\"",
        )

        val kakaoRestApiKey = requireProperty("kakao.rest.api.key", "KAKAO_REST_API_KEY")

        buildConfigField(
            type = "String",
            name = "KAKAO_REST_API_KEY",
            value = "\"$kakaoRestApiKey\"",
        )
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
            isDebuggable = true

            val devUrl = requireProperty("bitnagil.dev.url", "BITNAGIL_DEV_URL")
            buildConfigField("String", "BASE_URL", "\"$devUrl\"")
        }

        release {
            val prodUrl = requireProperty("bitnagil.prod.url", "BITNAGIL_PROD_URL")
            buildConfigField("String", "BASE_URL", "\"$prodUrl\"")

            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core.analytics)
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
    implementation(libs.bundles.coil)
}
