package com.threegap.bitnagil.domain.auth.repository

import com.threegap.bitnagil.domain.auth.model.AuthSession
import com.threegap.bitnagil.domain.auth.model.TermsAgreement

interface AuthRepository {
    suspend fun login(socialAccessToken: String, socialType: String): Result<AuthSession>
    suspend fun submitAgreement(termsAgreement: TermsAgreement): Result<Unit>
    suspend fun logout(): Result<Unit>
    suspend fun withdrawal(): Result<Unit>

    suspend fun reissueToken(refreshToken: String): Result<AuthSession>
    suspend fun getRefreshToken(): String?
    suspend fun updateAuthToken(accessToken: String, refreshToken: String): Result<Unit>
    suspend fun clearAuthToken(): Result<Unit>
}
