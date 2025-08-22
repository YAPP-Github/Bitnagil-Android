package com.threegap.bitnagil.domain.version.repository

import com.threegap.bitnagil.domain.version.model.UpdateRequirement

interface VersionRepository {
    suspend fun checkVersion(): Result<UpdateRequirement>
}
