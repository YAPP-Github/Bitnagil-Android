package com.threegap.bitnagil.convention.extension

import java.util.Properties
import org.gradle.api.GradleException
import org.gradle.api.Project

private const val LOCAL_PROPERTIES_FILE_NAME = "local.properties"
private const val LOCAL_PROPERTIES_EXTRA_KEY = "bitnagilLocalProperties"

private fun Project.loadLocalProperties(): Properties =
    Properties().apply {
        val localPropertiesFile = rootProject.file(LOCAL_PROPERTIES_FILE_NAME)
        if (localPropertiesFile.exists()) {
            localPropertiesFile.inputStream().use(::load)
        }
    }

/**
 * 모듈마다 한 번만 읽고 해당 프로젝트의 extra 에 담아둔다.
 * 데몬이 아니라 빌드마다 새로 만들어지는 저장소이므로 local.properties 를 수정하면 다음 빌드에 반영된다.
 */
private val Project.localProperties: Properties
    get() {
        val extra = extensions.extraProperties
        if (!extra.has(LOCAL_PROPERTIES_EXTRA_KEY)) {
            extra.set(LOCAL_PROPERTIES_EXTRA_KEY, loadLocalProperties())
        }
        return extra.get(LOCAL_PROPERTIES_EXTRA_KEY) as Properties
    }

/**
 * local.properties 값을 읽고, 없으면 환경 변수를 사용한다. 둘 다 없으면 null 을 반환한다.
 */
fun Project.propertyOrNull(propertyKey: String, environmentKey: String): String? =
    localProperties.getProperty(propertyKey) ?: System.getenv(environmentKey)

/**
 * [propertyOrNull] 과 동일하되, 값이 없거나 비어 있으면 설정 단계에서 실패한다.
 */
fun Project.requireProperty(propertyKey: String, environmentKey: String): String =
    propertyOrNull(propertyKey, environmentKey)?.takeIf(String::isNotBlank)
        ?: throw GradleException("$propertyKey 값이 없습니다.")
