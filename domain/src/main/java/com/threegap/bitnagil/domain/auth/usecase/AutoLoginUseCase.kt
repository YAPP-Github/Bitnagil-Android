package com.threegap.bitnagil.domain.auth.usecase

import com.threegap.bitnagil.domain.auth.model.UserRole
import com.threegap.bitnagil.domain.auth.repository.AuthRepository
import javax.inject.Inject

class AutoLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): UserRole {
        val refreshToken = authRepository.getRefreshToken()
        if (refreshToken.isNullOrEmpty()) return UserRole.UNKNOWN

        return authRepository.reissueToken(refreshToken)
            .onSuccess { authSession ->
                authRepository.updateAuthToken(
                    accessToken = authSession.accessToken,
                    refreshToken = authSession.refreshToken,
                )
            }
            .onFailure {
                authRepository.clearAuthToken()
            }
            .fold(
                onSuccess = { authSession -> authSession.role },
                onFailure = { UserRole.UNKNOWN },
            )
    }
}
