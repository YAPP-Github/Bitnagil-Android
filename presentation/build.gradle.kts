plugins {
    alias(libs.plugins.bitnagil.android.library)
    alias(libs.plugins.bitnagil.android.compose.library)
    alias(libs.plugins.bitnagil.android.hilt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.threegap.bitnagil.presentation"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.domain)

    implementation(libs.bundles.androidx.core)
    implementation(libs.bundles.orbit)
    implementation(libs.kakao.v2.user)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.orbit.test)
}
