package com.threegap.bitnagil.datastore.auth.storage

import androidx.datastore.core.DataStore
import com.threegap.bitnagil.datastore.auth.model.AuthToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class AuthTokenDataStoreImpl(
    private val dataStore: DataStore<AuthToken>,
) : AuthTokenDataStore {
    override val tokenFlow: Flow<AuthToken> = dataStore.data

    override suspend fun hasToken(): Boolean {
        return try {
            val currentToken = dataStore.data.firstOrNull()
            currentToken?.let {
                !it.accessToken.isNullOrEmpty() && !it.refreshToken.isNullOrEmpty()
            } ?: false
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun updateAuthToken(accessToken: String, refreshToken: String) {
        try {
            dataStore.updateData {
                AuthToken(accessToken, refreshToken)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateAccessToken(accessToken: String) {
        try {
            dataStore.updateData { currentToken ->
                currentToken.copy(accessToken = accessToken)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateRefreshToken(refreshToken: String) {
        try {
            dataStore.updateData { currentToken ->
                currentToken.copy(refreshToken = refreshToken)
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun clearAuthToken() {
        try {
            dataStore.updateData { AuthToken() }
        } catch (e: Exception) {
            throw e
        }
    }

    companion object {
        private const val TAG = "AuthTokenDataStore"
    }
}
