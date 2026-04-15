package com.threegap.bitnagil.domain.auth.usecase

import com.threegap.bitnagil.domain.auth.repository.AuthRepository
import com.threegap.bitnagil.domain.emotion.repository.EmotionRepository
import com.threegap.bitnagil.domain.user.repository.UserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val emotionRepository: EmotionRepository,
) {
    suspend operator fun invoke(): Result<Unit> =
        authRepository.logout().onSuccess {
            userRepository.clearCache()
            emotionRepository.clearCache()
        }
}
