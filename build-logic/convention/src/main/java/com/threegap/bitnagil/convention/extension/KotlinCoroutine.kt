package com.threegap.bitnagil.convention.extension

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureKotlinCoroutine() {

    dependencies {
        "implementation"(libs.findBundle("coroutine").get())
    }
}
