package com.threegap.bitnagil.datastore.auth.storage

import com.threegap.bitnagil.datastore.auth.model.AuthToken
import kotlinx.coroutines.flow.Flow

interface AuthTokenDataStore {
    val tokenFlow: Flow<AuthToken>

    suspend fun hasToken(): Boolean

    suspend fun updateAuthToken(accessToken: String, refreshToken: String)

    suspend fun updateAccessToken(accessToken: String)

    suspend fun updateRefreshToken(refreshToken: String)

    suspend fun clearAuthToken()
}
