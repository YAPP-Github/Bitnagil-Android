package com.threegap.bitnagil.domain.version.usecase

import com.threegap.bitnagil.domain.version.repository.VersionRepository
import javax.inject.Inject

class CheckUpdateRequirementUseCase @Inject constructor(
    private val versionRepository: VersionRepository,
) {
    suspend operator fun invoke(): Result<Boolean> =
        versionRepository.checkVersion().map { it.isForced }
}
