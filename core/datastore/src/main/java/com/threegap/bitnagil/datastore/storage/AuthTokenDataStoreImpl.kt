package com.threegap.bitnagil.datastore.storage

import android.util.Log
import androidx.datastore.core.DataStore
import com.threegap.bitnagil.datastore.model.AuthToken
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class AuthTokenDataStoreImpl
    @Inject
    constructor(
        private val dataStore: DataStore<AuthToken>,
    ) : AuthTokenDataStore {
        override val tokenFlow: Flow<AuthToken> = dataStore.data

        override suspend fun updateAuthToken(authToken: AuthToken): AuthToken =
            runCatching {
                dataStore.updateData { authToken }
            }.fold(
                onSuccess = { it },
                onFailure = {
                    Log.e(TAG, "updateAuthToken failed:", it)
                    throw it
                },
            )

        override suspend fun updateAccessToken(accessToken: String): AuthToken =
            runCatching {
                dataStore.updateData { authToke ->
                    authToke.copy(accessToken = accessToken)
                }
            }.fold(
                onSuccess = { it },
                onFailure = {
                    Log.e(TAG, "updateAccessToken failed:", it)
                    throw it
                },
            )

        override suspend fun updateRefreshToken(refreshToken: String): AuthToken =
            runCatching {
                dataStore.updateData { authToken ->
                    authToken.copy(refreshToken = refreshToken)
                }
            }.fold(
                onSuccess = { it },
                onFailure = {
                    Log.e(TAG, "updateRefreshToken failed:", it)
                    throw it
                },
            )

        override suspend fun clearAuthToken(): AuthToken =
            runCatching {
                dataStore.updateData { AuthToken() }
            }.fold(
                onSuccess = { it },
                onFailure = {
                    Log.e(TAG, "clearAuthToken failed:", it)
                    throw it
                },
            )

        companion object {
            private const val TAG = "AuthTokenDataStore"
        }
    }
