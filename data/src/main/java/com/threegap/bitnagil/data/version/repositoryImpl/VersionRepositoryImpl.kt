package com.threegap.bitnagil.data.version.repositoryImpl

import com.threegap.bitnagil.data.version.datasource.VersionDataSource
import com.threegap.bitnagil.data.version.model.response.toDomain
import com.threegap.bitnagil.domain.version.model.UpdateRequirement
import com.threegap.bitnagil.domain.version.repository.VersionRepository
import javax.inject.Inject

class VersionRepositoryImpl @Inject constructor(
    private val versionDataSource: VersionDataSource,
) : VersionRepository {

    override suspend fun checkVersion(): Result<UpdateRequirement> =
        versionDataSource.checkVersion().map { it.toDomain() }
}
