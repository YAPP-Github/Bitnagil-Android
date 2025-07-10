package com.threegap.bitnagil.domain.auth.usecase

import com.threegap.bitnagil.domain.auth.model.AuthSession
import com.threegap.bitnagil.domain.auth.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(socialAccessToken: String, socialType: String): Result<AuthSession> =
        authRepository.login(socialAccessToken, socialType)
            .mapCatching { authSession ->
                authRepository.updateAuthToken(authSession.accessToken, authSession.refreshToken)
                    .getOrThrow()
                authSession
            }
}
