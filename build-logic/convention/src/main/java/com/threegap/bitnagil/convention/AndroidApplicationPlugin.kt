package com.threegap.bitnagil.convention

import com.android.build.api.dsl.ApplicationExtension
import com.threegap.bitnagil.convention.extension.basePackage
import com.threegap.bitnagil.convention.extension.configureAppVersion
import com.threegap.bitnagil.convention.extension.configureComposeAndroid
import com.threegap.bitnagil.convention.extension.configureKotlinAndroid
import com.threegap.bitnagil.convention.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.plugin.compose")
            apply("org.jlleitschuh.gradle.ktlint")
        }

        extensions.configure<ApplicationExtension> {
            configureKotlinAndroid(this)
            configureComposeAndroid(this)
            configureAppVersion(this)
            with(defaultConfig) {
                applicationId = basePackage
                targetSdk = libs.findVersion("targetSdk").get().requiredVersion.toInt()
            }
        }
    }
}
