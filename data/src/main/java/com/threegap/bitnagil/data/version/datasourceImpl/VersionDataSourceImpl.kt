package com.threegap.bitnagil.data.version.datasourceImpl

import com.threegap.bitnagil.data.BuildConfig
import com.threegap.bitnagil.data.version.datasource.VersionDataSource
import com.threegap.bitnagil.data.version.model.response.VersionCheckResponse
import com.threegap.bitnagil.data.version.service.VersionService
import javax.inject.Inject

class VersionDataSourceImpl @Inject constructor(
    private val versionService: VersionService,
) : VersionDataSource {

    override suspend fun checkVersion(): Result<VersionCheckResponse> =
        versionService.checkVersion(
            majorVersion = BuildConfig.VERSION_MAJOR,
            minorVersion = BuildConfig.VERSION_MINOR,
            patchVersion = BuildConfig.VERSION_PATCH,
        )
}
