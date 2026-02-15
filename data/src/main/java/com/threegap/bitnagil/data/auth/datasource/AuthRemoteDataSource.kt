package com.threegap.bitnagil.data.auth.datasource

import com.threegap.bitnagil.data.auth.model.request.LoginRequest
import com.threegap.bitnagil.data.auth.model.request.TermsAgreementRequest
import com.threegap.bitnagil.data.auth.model.response.LoginResponse

interface AuthRemoteDataSource {
    suspend fun login(socialAccessToken: String, loginRequest: LoginRequest): Result<LoginResponse>
    suspend fun submitAgreement(termsAgreementRequest: TermsAgreementRequest): Result<Unit>
    suspend fun logout(): Result<Unit>
    suspend fun withdrawal(reason: String): Result<Unit>
    suspend fun reissueToken(refreshToken: String): Result<LoginResponse>
}
