package com.threegap.bitnagil.data.version.datasource

import com.threegap.bitnagil.data.version.model.response.VersionCheckResponseDto

interface VersionDataSource {
    suspend fun checkVersion(): Result<VersionCheckResponseDto>
}
