package com.threegap.bitnagil.convention

import com.threegap.bitnagil.convention.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KotlinSerializationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

        dependencies {
            "implementation"(libs.findLibrary("kotlinx-serialization-json").get())
        }
    }
}
