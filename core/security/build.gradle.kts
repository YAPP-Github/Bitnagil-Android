plugins {
    alias(libs.plugins.bitnagil.android.library)
    alias(libs.plugins.bitnagil.android.hilt)
}

android {
    namespace = "com.threegap.bitnagil.security"
}

dependencies {
    testImplementation(libs.androidx.junit)
}
