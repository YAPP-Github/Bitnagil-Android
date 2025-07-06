package com.threegap.bitnagil.datastore.auth.storage

import com.threegap.bitnagil.datastore.auth.model.AuthToken
import kotlinx.coroutines.flow.Flow

interface AuthTokenDataStore {
    val tokenFlow: Flow<AuthToken>

    suspend fun updateAuthToken(authToken: AuthToken): AuthToken

    suspend fun updateAccessToken(accessToken: String): AuthToken

    suspend fun updateRefreshToken(refreshToken: String): AuthToken

    suspend fun clearAuthToken(): AuthToken
}
