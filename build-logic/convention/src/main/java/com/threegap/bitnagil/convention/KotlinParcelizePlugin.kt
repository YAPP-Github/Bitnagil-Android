package com.threegap.bitnagil.convention

import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinParcelizePlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply("org.jetbrains.kotlin.plugin.parcelize")
    }
}
