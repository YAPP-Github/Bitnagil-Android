package com.threegap.bitnagil.convention.extension

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType

internal fun Project.configureApplicationId() {
    val applicationId = "com.threegap.bitnagil"

    extensions.findByType<ApplicationExtension>()?.let { appExtension ->
        appExtension.defaultConfig {
            this.applicationId = applicationId
        }
    }

    extensions.findByType<LibraryExtension>()?.let { libExtension ->
        libExtension.apply {
            defaultConfig {
                buildConfigField("String", "APPLICATION_ID", "\"$applicationId\"")
            }
            buildFeatures {
                buildConfig = true
            }
        }
    }
}
