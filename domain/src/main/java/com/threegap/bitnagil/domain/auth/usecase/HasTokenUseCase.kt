package com.threegap.bitnagil.domain.auth.usecase

import com.threegap.bitnagil.domain.auth.repository.AuthRepository
import javax.inject.Inject

class HasTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): Boolean = authRepository.hasToken()
}
