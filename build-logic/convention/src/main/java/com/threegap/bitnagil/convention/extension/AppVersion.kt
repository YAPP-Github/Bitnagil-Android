package com.threegap.bitnagil.convention.extension

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureAppVersion() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    val major = libs.findVersion("versionMajor").get().requiredVersion
    val minor = libs.findVersion("versionMinor").get().requiredVersion
    val patch = libs.findVersion("versionPatch").get().requiredVersion

    extensions.findByType<ApplicationExtension>()?.let { appExtension ->
        appExtension.defaultConfig {
            versionName = "$major.$minor.$patch"
            buildConfigField("int", "VERSION_MAJOR", major)
            buildConfigField("int", "VERSION_MINOR", minor)
            buildConfigField("int", "VERSION_PATCH", patch)
        }
    }

    extensions.findByType<LibraryExtension>()?.let { libExtension ->
        libExtension.apply {
            defaultConfig {
                buildConfigField("int", "VERSION_MAJOR", major)
                buildConfigField("int", "VERSION_MINOR", minor)
                buildConfigField("int", "VERSION_PATCH", patch)
            }
            buildFeatures {
                buildConfig = true
            }
        }
    }
}
