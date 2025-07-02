package com.threegap.bitnagil.network.auth

import com.threegap.bitnagil.datastore.storage.AuthTokenDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStore: AuthTokenDataStore,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = runBlocking { dataStore.tokenFlow.first().accessToken }
        if (token.isNullOrBlank()) {
            return chain.proceed(originalRequest)
        }

        val newRequest = originalRequest.newBuilder()
            .addHeader(HEADER_AUTHORIZATION, "$TOKEN_PREFIX $token")
            .build()

        return chain.proceed(newRequest)
    }

    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val TOKEN_PREFIX = "Bearer "
    }
}
