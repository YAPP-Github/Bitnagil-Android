package com.threegap.bitnagil.domain.auth.usecase

import com.threegap.bitnagil.domain.auth.repository.AuthRepository
import com.threegap.bitnagil.domain.user.repository.UserRepository
import javax.inject.Inject

class WithdrawalUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(reason: String): Result<Unit> =
        authRepository.withdrawal(reason).onSuccess {
            userRepository.clearCache()
        }
}
