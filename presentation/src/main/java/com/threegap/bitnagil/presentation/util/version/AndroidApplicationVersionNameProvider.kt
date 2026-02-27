package com.threegap.bitnagil.presentation.util.version

import com.threegap.bitnagil.presentation.BuildConfig
import javax.inject.Inject

class AndroidApplicationVersionNameProvider @Inject constructor() : VersionNameProvider {
    override fun getVersionName(): String {
        val majorVersion = BuildConfig.VERSION_MAJOR
        val minorVersion = BuildConfig.VERSION_MINOR
        val patchVersion = BuildConfig.VERSION_PATCH

        return "$majorVersion.$minorVersion.$patchVersion"
    }
}
