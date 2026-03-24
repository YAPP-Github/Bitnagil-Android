plugins {
    alias(libs.plugins.bitnagil.android.library)
    alias(libs.plugins.bitnagil.android.compose.library)
    alias(libs.plugins.bitnagil.android.hilt)
    alias(libs.plugins.bitnagil.kotlin.parcelize)
    alias(libs.plugins.bitnagil.kotlin.serialization)
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.domain)

    implementation(libs.bundles.androidx.core)
    implementation(libs.bundles.orbit)
    implementation(libs.kakao.v2.user)
    implementation(libs.lottie.compose)
    implementation(libs.bundles.coil)
    implementation(libs.accompanist.permissions)
    implementation(libs.bundles.app.update)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.orbit.test)
}
