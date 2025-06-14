plugins {
    alias(libs.plugins.bitnagil.android.library)
    alias(libs.plugins.bitnagil.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.threegap.bitnagil.datastore"
}

dependencies {
    implementation(libs.androidx.datastore)
}
