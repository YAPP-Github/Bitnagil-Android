package com.threegap.bitnagil.data.auth.mapper

import com.threegap.bitnagil.data.auth.model.request.TermsAgreementRequestDto
import com.threegap.bitnagil.data.auth.model.response.LoginResponseDto
import com.threegap.bitnagil.domain.auth.model.AuthSession
import com.threegap.bitnagil.domain.auth.model.TermsAgreement
import com.threegap.bitnagil.domain.auth.model.UserRole

// toDomain
internal fun LoginResponseDto.toDomain() =
    AuthSession(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        role = UserRole.from(this.role),
    )

// toDto
internal fun TermsAgreement.toDto() =
    TermsAgreementRequestDto(
        agreedToTermsOfService = this.agreedToTermsOfService,
        agreedToPrivacyPolicy = this.agreedToPrivacyPolicy,
        isOverFourteen = this.isOverFourteen,
    )
