package com.threegap.bitnagil.convention

import com.threegap.bitnagil.convention.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply {
            apply("dagger.hilt.android.plugin")
            apply("com.google.devtools.ksp")
        }

        dependencies {
            "implementation"(libs.findLibrary("hilt.android").get())
            "ksp"(libs.findLibrary("hilt.compiler").get())
        }
    }
}
