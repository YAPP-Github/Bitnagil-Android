plugins {
    alias(libs.plugins.bitnagil.kotlin)
    alias(libs.plugins.bitnagil.kotlin.serialization)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.javax.inject)
    testImplementation(libs.junit)
}
