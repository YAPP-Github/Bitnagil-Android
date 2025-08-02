package com.threegap.bitnagil.data.auth.datasource

import com.threegap.bitnagil.data.auth.model.request.LoginRequestDto
import com.threegap.bitnagil.data.auth.model.request.TermsAgreementRequestDto
import com.threegap.bitnagil.data.auth.model.response.LoginResponseDto

interface AuthRemoteDataSource {
    suspend fun login(socialAccessToken: String, loginRequestDto: LoginRequestDto): Result<LoginResponseDto>

    suspend fun submitAgreement(termsAgreementRequestDto: TermsAgreementRequestDto): Result<Unit>

    suspend fun logout(): Result<Unit>

    suspend fun withdrawal(): Result<Unit>
}
