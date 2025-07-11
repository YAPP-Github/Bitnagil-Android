package com.threegap.bitnagil.domain.auth.repository

import com.threegap.bitnagil.domain.auth.model.AuthSession

interface AuthRepository {
    suspend fun login(socialAccessToken: String, socialType: String): Result<AuthSession>

    suspend fun hasToken(): Boolean

    suspend fun updateAuthToken(accessToken: String, refreshToken: String): Result<Unit>
}
