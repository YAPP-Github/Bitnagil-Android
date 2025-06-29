plugins {
    alias(libs.plugins.bitnagil.android.library)
    alias(libs.plugins.bitnagil.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.threegap.bitnagil.datastore"
}

dependencies {
    implementation(projects.core.security)

    implementation(libs.androidx.datastore.preferences)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.androidx.junit)
    testImplementation(libs.kotlin.coroutines.test)
}
