plugins {
    alias(libs.plugins.bitnagil.android.library)
    alias(libs.plugins.bitnagil.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.threegap.bitnagil.data"
}

dependencies {
    implementation(projects.core.network)
    implementation(projects.core.datastore)
    implementation(projects.domain)

    implementation(libs.kotlinx.serialization.json)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.okhttp)
    implementation(platform(libs.retrofit.bom))
    implementation(libs.bundles.retrofit)
}
