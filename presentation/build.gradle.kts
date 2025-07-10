plugins {
    alias(libs.plugins.bitnagil.android.library)
    alias(libs.plugins.bitnagil.android.compose.library)
    alias(libs.plugins.bitnagil.android.hilt)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.threegap.bitnagil.presentation"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.domain)

    implementation(libs.bundles.androidx.core)
    implementation(libs.bundles.orbit)
    implementation(libs.bundles.coil)
    implementation(libs.kakao.v2.user)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.orbit.test)
}
