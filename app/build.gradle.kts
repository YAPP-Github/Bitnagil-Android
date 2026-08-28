import java.util.Properties

plugins {
    alias(libs.plugins.bitnagil.android.application)
    alias(libs.plugins.bitnagil.android.hilt)
    alias(libs.plugins.bitnagil.kotlin.serialization)
}

android {
    val properties =
        Properties().apply {
            val propFile = rootProject.file("local.properties")
            if (propFile.exists()) {
                load(propFile.inputStream())
            }
        }

    signingConfigs {
        create("release") {
            keyAlias = properties["release.key.alias"] as? String
                ?: System.getenv("RELEASE_KEY_ALIAS")
                ?: throw GradleException("RELEASE_KEY_ALIAS 값이 없습니다.")
            keyPassword = properties["release.key.password"] as? String
                ?: System.getenv("RELEASE_KEY_PASSWORD")
                ?: throw GradleException("RELEASE_KEY_PASSWORD 값이 없습니다.")
            storePassword = properties["release.keystore.password"] as? String
                ?: System.getenv("RELEASE_KEYSTORE_PASSWORD")
                ?: throw GradleException("RELEASE_KEYSTORE_PASSWORD 값이 없습니다.")
            storeFile = File("${properties["release.keystore.path"]}")
        }
    }

    defaultConfig {
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

        val kakaoRestApiKey =
            (properties["kakao.rest.api.key"] as? String)
                ?: System.getenv("KAKAO_REST_API_KEY")
                ?: throw GradleException("KAKAO_REST_API_KEY 값이 없습니다.")

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

            val devUrl = properties["bitnagil.dev.url"] as? String
                ?: System.getenv("BITNAGIL_DEV_URL")
                ?: throw GradleException("bitnagil.dev.url 값이 없습니다.")
            buildConfigField("String", "BASE_URL", "\"$devUrl\"")

            val facebookDebugAppId = properties["facebook.debug.app.id"] as? String
                ?: System.getenv("FACEBOOK_DEBUG_APP_ID")
                ?: throw GradleException("facebook.debug.app.id 값이 없습니다.")
            val facebookDebugClientToken = properties["facebook.debug.client.token"] as? String
                ?: System.getenv("FACEBOOK_DEBUG_CLIENT_TOKEN")
                ?: throw GradleException("facebook.debug.client.token 값이 없습니다.")

            resValue("string", "facebook_app_id", facebookDebugAppId)
            resValue("string", "facebook_client_token", facebookDebugClientToken)
        }

        release {
            val prodUrl = properties["bitnagil.prod.url"] as? String
                ?: System.getenv("BITNAGIL_PROD_URL")
                ?: throw GradleException("bitnagil.prod.url 값이 없습니다.")
            buildConfigField("String", "BASE_URL", "\"$prodUrl\"")

            val facebookAppId = properties["facebook.app.id"] as? String
                ?: System.getenv("FACEBOOK_APP_ID")
                ?: throw GradleException("facebook.app.id 값이 없습니다.")
            val facebookClientToken = properties["facebook.client.token"] as? String
                ?: System.getenv("FACEBOOK_CLIENT_TOKEN")
                ?: throw GradleException("facebook.client.token 값이 없습니다.")

            resValue("string", "facebook_app_id", facebookAppId)
            resValue("string", "facebook_client_token", facebookClientToken)

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
        resValues = true
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
    implementation(libs.facebook.core)
    implementation(platform(libs.retrofit.bom))
    implementation(libs.bundles.retrofit)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.coil)
}
