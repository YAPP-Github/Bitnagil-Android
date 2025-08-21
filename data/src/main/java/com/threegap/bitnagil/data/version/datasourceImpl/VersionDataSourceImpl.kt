package com.threegap.bitnagil.data.version.datasourceImpl

import com.threegap.bitnagil.data.BuildConfig
import com.threegap.bitnagil.data.common.safeApiCall
import com.threegap.bitnagil.data.version.datasource.VersionDataSource
import com.threegap.bitnagil.data.version.model.response.VersionCheckResponseDto
import com.threegap.bitnagil.data.version.service.VersionService
import javax.inject.Inject

class VersionDataSourceImpl @Inject constructor(
    private val versionService: VersionService,
) : VersionDataSource {

    override suspend fun checkVersion(): Result<VersionCheckResponseDto> =
        safeApiCall {
            versionService.checkVersion(
                majorVersion = BuildConfig.VERSION_MAJOR,
                minorVersion = BuildConfig.VERSION_MINOR,
                patchVersion = BuildConfig.VERSION_PATCH,
            )
        }
}
