package com.threegap.bitnagil.data.version.datasource

import com.threegap.bitnagil.data.version.model.response.VersionCheckResponse

interface VersionDataSource {
    suspend fun checkVersion(): Result<VersionCheckResponse>
}
