import com.threegap.bitnagil.convention.extension.requireProperty

plugins {
    alias(libs.plugins.bitnagil.android.library)
}

android {
    buildTypes {
        debug {
            resValue(
                type = "string",
                name = "facebook_app_id",
                value = requireProperty("facebook.debug.app.id", "FACEBOOK_DEBUG_APP_ID"),
            )
            resValue(
                type = "string",
                name = "facebook_client_token",
                value = requireProperty("facebook.debug.client.token", "FACEBOOK_DEBUG_CLIENT_TOKEN"),
            )
        }

        release {
            resValue(
                type = "string",
                name = "facebook_app_id",
                value = requireProperty("facebook.app.id", "FACEBOOK_APP_ID"),
            )
            resValue(
                type = "string",
                name = "facebook_client_token",
                value = requireProperty("facebook.client.token", "FACEBOOK_CLIENT_TOKEN"),
            )
        }
    }

    buildFeatures {
        resValues = true
    }
}

dependencies {
    implementation(libs.facebook.core)
}
