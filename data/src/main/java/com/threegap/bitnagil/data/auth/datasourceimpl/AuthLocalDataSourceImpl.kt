package com.threegap.bitnagil.data.auth.datasourceimpl

import com.threegap.bitnagil.data.auth.datasource.AuthLocalDataSource
import com.threegap.bitnagil.datastore.auth.storage.AuthTokenDataStore
import javax.inject.Inject

class AuthLocalDataSourceImpl @Inject constructor(
    private val authTokenDataStore: AuthTokenDataStore,
) : AuthLocalDataSource {

    override suspend fun hasToken(): Boolean = authTokenDataStore.hasToken()

    override suspend fun updateAuthToken(accessToken: String, refreshToken: String): Result<Unit> =
        runCatching {
            authTokenDataStore.updateAuthToken(
                accessToken = accessToken,
                refreshToken = refreshToken,
            )
        }
}
