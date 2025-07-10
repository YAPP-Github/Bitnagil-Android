package com.threegap.bitnagil.data.auth.datasource

interface AuthLocalDataSource {
    suspend fun updateAuthToken(accessToken: String, refreshToken: String): Result<Unit>
}
