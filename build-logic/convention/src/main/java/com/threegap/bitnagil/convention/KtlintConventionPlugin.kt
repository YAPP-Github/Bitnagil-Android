package com.threegap.bitnagil.convention

import com.threegap.bitnagil.convention.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jlleitschuh.gradle.ktlint.KtlintExtension

class KtlintConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply("org.jlleitschuh.gradle.ktlint")

        extensions.configure<KtlintExtension> {
            version.set(libs.findVersion("ktlintEngine").get().requiredVersion)
            verbose.set(true)
            filter { exclude { element -> element.file.path.contains("generated/") } }
        }
    }
}
