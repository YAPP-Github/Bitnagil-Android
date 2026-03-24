package com.threegap.bitnagil.convention.extension

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project

internal fun Project.configureAppVersion(extension: ApplicationExtension) {
    val major = libs.findVersion("versionMajor").get().requiredVersion
    val minor = libs.findVersion("versionMinor").get().requiredVersion
    val patch = libs.findVersion("versionPatch").get().requiredVersion

    extension.defaultConfig {
        versionCode = libs.findVersion("versionCode").get().requiredVersion.toInt()
        versionName = "$major.$minor.$patch"
        buildConfigField("int", "VERSION_MAJOR", major)
        buildConfigField("int", "VERSION_MINOR", minor)
        buildConfigField("int", "VERSION_PATCH", patch)
    }
}
