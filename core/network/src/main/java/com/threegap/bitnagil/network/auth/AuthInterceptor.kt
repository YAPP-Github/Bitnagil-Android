package com.threegap.bitnagil.network.auth

import com.threegap.bitnagil.network.token.TokenProvider
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(
    private val tokenProvider: TokenProvider,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val noToken = originalRequest.header(HEADER_NO_SERVICE_TOKEN)

        if (noToken == "true") {
            return chain.proceed(removeNoTokenHeader(originalRequest))
        }

        val token = runBlocking { tokenProvider.getAccessToken() }
        if (token.isNullOrBlank()) {
            return chain.proceed(originalRequest)
        }

        val newRequest = originalRequest.newBuilder()
            .addHeader(HEADER_AUTHORIZATION, "$TOKEN_PREFIX $token")
            .build()

        return chain.proceed(newRequest)
    }

    private fun removeNoTokenHeader(request: Request): Request =
        request.newBuilder()
            .removeHeader(HEADER_NO_SERVICE_TOKEN)
            .build()

    companion object {
        private const val HEADER_NO_SERVICE_TOKEN = "No-Service-Token"
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val TOKEN_PREFIX = "Bearer"
    }
}
