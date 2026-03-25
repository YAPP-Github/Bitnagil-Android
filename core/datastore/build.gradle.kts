plugins {
    alias(libs.plugins.bitnagil.android.library)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    api(libs.androidx.datastore.preferences)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.androidx.junit)
    testImplementation(libs.kotlin.coroutines.test)
}
