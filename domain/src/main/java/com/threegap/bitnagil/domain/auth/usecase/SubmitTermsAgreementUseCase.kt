package com.threegap.bitnagil.domain.auth.usecase

import com.threegap.bitnagil.domain.auth.model.TermsAgreement
import com.threegap.bitnagil.domain.auth.repository.AuthRepository
import javax.inject.Inject

class SubmitTermsAgreementUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(agreement: TermsAgreement): Result<Unit> {
        if (!agreement.isValid()) {
            return Result.failure(IllegalArgumentException("모든 필수 약관에 동의해야 합니다."))
        }
        return authRepository.submitAgreement(agreement)
    }
}
