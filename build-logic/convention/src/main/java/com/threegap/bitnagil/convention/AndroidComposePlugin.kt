package com.threegap.bitnagil.convention

import com.android.build.gradle.LibraryExtension
import com.threegap.bitnagil.convention.extension.configureComposeAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidComposePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply {
            apply("org.jetbrains.kotlin.plugin.compose")
        }

        extensions.configure<LibraryExtension> {
            configureComposeAndroid(this)
        }
    }
}
