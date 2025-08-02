package com.threegap.bitnagil.data.auth.datasource

interface AuthLocalDataSource {
    suspend fun hasToken(): Boolean

    suspend fun updateAuthToken(accessToken: String, refreshToken: String): Result<Unit>

    suspend fun clearAuthToken(): Result<Unit>
}
