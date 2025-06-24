plugins {
    alias(libs.plugins.bitnagil.android.library)
}

android {
    namespace = "com.threegap.bitnagil.security"
}

dependencies {
    testImplementation(libs.androidx.junit)
}
