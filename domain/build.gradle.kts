plugins {
    alias(libs.plugins.bitnagil.kotlin)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.javax.inject)
    testImplementation(libs.junit)
}
