package com.threegap.bitnagil.convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class HiltPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply {
            apply("dagger.hilt.android.plugin")
            apply("com.google.devtools.ksp")
        }

        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        dependencies {
            "implementation"(libs.findLibrary("hilt.android").get())
            "ksp"(libs.findLibrary("hilt.compiler").get())
        }
    }
}
