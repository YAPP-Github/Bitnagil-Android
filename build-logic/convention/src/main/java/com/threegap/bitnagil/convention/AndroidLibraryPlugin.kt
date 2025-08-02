package com.threegap.bitnagil.convention

import com.android.build.gradle.LibraryExtension
import com.threegap.bitnagil.convention.extension.configureKotlinAndroid
import com.threegap.bitnagil.convention.extension.configureKotlinCoroutine
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
        }

        extensions.configure<LibraryExtension> {
            configureKotlinAndroid(this)
            configureKotlinCoroutine(this)
        }
    }
}
