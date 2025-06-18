plugins {
    alias(libs.plugins.bitnagil.android.library)
    alias(libs.plugins.bitnagil.android.compose.library)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.threegap.bitnagil.presentation"
}

dependencies {
    implementation(projects.core.designsystem)

    implementation(libs.bundles.androidx.core)
    implementation(libs.bundles.orbit)
}
