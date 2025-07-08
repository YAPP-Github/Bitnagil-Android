package com.threegap.bitnagil.datastore.auth.storage

import android.util.Log
import androidx.datastore.core.DataStore
import com.threegap.bitnagil.datastore.auth.model.AuthToken
import kotlinx.coroutines.flow.Flow

class AuthTokenDataStoreImpl(
    private val dataStore: DataStore<AuthToken>,
) : AuthTokenDataStore {
    override val tokenFlow: Flow<AuthToken> = dataStore.data

    override suspend fun updateAuthToken(accessToken: String, refreshToken: String) {
        try {
            dataStore.updateData {
                AuthToken(accessToken, refreshToken)
            }
            Log.d(TAG, "Auth token updated successfully")
        } catch (e: Exception) {
            Log.e(TAG, "updateAuthToken failed:", e)
            throw e
        }
    }

    override suspend fun updateAccessToken(accessToken: String) {
        try {
            dataStore.updateData { currentToken ->
                currentToken.copy(accessToken = accessToken)
            }
            Log.d(TAG, "Access token updated successfully")
        } catch (e: Exception) {
            Log.e(TAG, "updateAccessToken failed:", e)
            throw e
        }
    }

    override suspend fun updateRefreshToken(refreshToken: String) {
        try {
            dataStore.updateData { currentToken ->
                currentToken.copy(refreshToken = refreshToken)
            }
            Log.d(TAG, "Refresh token updated successfully")
        } catch (e: Exception) {
            Log.e(TAG, "updateRefreshToken failed:", e)
            throw e
        }
    }

    override suspend fun clearAuthToken() {
        try {
            dataStore.updateData { AuthToken() }
            Log.d(TAG, "Auth token cleared successfully")
        } catch (e: Exception) {
            Log.e(TAG, "clearAuthToken failed:", e)
            throw e
        }
    }

    companion object {
        private const val TAG = "AuthTokenDataStore"
    }
}
