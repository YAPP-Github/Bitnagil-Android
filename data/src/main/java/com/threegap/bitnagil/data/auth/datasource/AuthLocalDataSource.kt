package com.threegap.bitnagil.data.auth.datasource

interface AuthLocalDataSource {
    suspend fun getRefreshToken(): String?
    suspend fun updateAuthToken(accessToken: String, refreshToken: String): Result<Unit>
    suspend fun clearAuthToken(): Result<Unit>
}
