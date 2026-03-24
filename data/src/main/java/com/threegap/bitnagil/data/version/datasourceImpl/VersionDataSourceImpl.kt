package com.threegap.bitnagil.data.version.datasourceImpl

import com.threegap.bitnagil.data.version.datasource.VersionDataSource
import com.threegap.bitnagil.data.version.model.response.VersionCheckResponse
import com.threegap.bitnagil.data.version.service.VersionService
import javax.inject.Inject
import javax.inject.Named

class VersionDataSourceImpl @Inject constructor(
    private val versionService: VersionService,
    @param:Named("versionMajor") private val majorVersion: Int,
    @param:Named("versionMinor") private val minorVersion: Int,
    @param:Named("versionPatch") private val patchVersion: Int,
) : VersionDataSource {

    override suspend fun checkVersion(): Result<VersionCheckResponse> =
        versionService.checkVersion(
            majorVersion = majorVersion,
            minorVersion = minorVersion,
            patchVersion = patchVersion,
        )
}
