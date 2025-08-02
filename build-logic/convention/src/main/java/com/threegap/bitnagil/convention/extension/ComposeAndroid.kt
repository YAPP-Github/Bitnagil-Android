package com.threegap.bitnagil.convention.extension

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

internal fun Project.configureComposeAndroid(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        extensions.getByType<ComposeCompilerGradlePluginExtension>().apply {
            includeSourceInformation.set(true)
        }

        dependencies {
            "implementation"(platform(libs.findLibrary("compose.bom").get()))
            "implementation"(libs.findBundle("compose").get())
            "implementation"(libs.findBundle("compose.lifecycle").get())
            "debugImplementation"(libs.findBundle("compose.debug").get())
        }
    }
}
