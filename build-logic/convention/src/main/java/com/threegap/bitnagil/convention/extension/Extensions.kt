package com.threegap.bitnagil.convention.extension

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal val Project.basePackage: String
    get() = libs.findVersion("applicationId").get().requiredVersion

internal val Project.namespace: String
    get() = if (path == ":app") basePackage else "$basePackage.${name.replace("-", "_")}"
