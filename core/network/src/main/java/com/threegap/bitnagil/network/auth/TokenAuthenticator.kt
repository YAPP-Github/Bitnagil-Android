package com.threegap.bitnagil.network.auth

import com.threegap.bitnagil.network.token.ReissueService
import com.threegap.bitnagil.network.token.TokenProvider
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val tokenProvider: TokenProvider,
    private val reissueService: ReissueService,
    private val onTokenExpired: (() -> Unit)? = null,
) : Authenticator {

    private val authMutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.request.header(AUTO_LOGIN_HEADER) != null) return null
        if (!shouldRetry(response)) return null

        val currentToken = runBlocking { tokenProvider.getAccessToken() }
        val authHeader = response.request.header(AUTHORIZATION)
        val requestToken = authHeader?.takeIf { it.startsWith("$TOKEN_PREFIX ") }
            ?.substring("$TOKEN_PREFIX ".length)

        if (!currentToken.isNullOrBlank() && !requestToken.isNullOrBlank() && currentToken != requestToken) {
            return buildRequestWithToken(response.request, currentToken)
        }

        return runBlocking {
            authMutex.withLock { refreshAndRetry(response) }
        }
    }

    private suspend fun refreshAndRetry(response: Response): Request? {
        val refreshToken = tokenProvider.getRefreshToken()

        if (refreshToken.isNullOrBlank()) {
            handleTokenExpiration()
            return null
        }

        return runCatching {
            reissueService.reissueToken(refreshToken)
        }.fold(
            onSuccess = { baseResponse ->
                if (baseResponse.data != null && baseResponse.code == SUCCESS_CODE) {
                    tokenProvider.saveTokens(
                        accessToken = baseResponse.data.accessToken,
                        refreshToken = baseResponse.data.refreshToken,
                    )
                    buildRequestWithToken(response.request, baseResponse.data.accessToken)
                } else {
                    handleTokenExpiration()
                    null
                }
            },
            onFailure = {
                handleTokenExpiration()
                null
            },
        )
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }

    private fun shouldRetry(response: Response): Boolean {
        return response.code == UNAUTHORIZED && responseCount(response) < MAX_RETRY
    }

    private fun buildRequestWithToken(originalRequest: Request, token: String): Request {
        return originalRequest.newBuilder()
            .header(AUTHORIZATION, "$TOKEN_PREFIX $token")
            .removeHeader(AUTO_LOGIN_HEADER)
            .build()
    }

    private suspend fun handleTokenExpiration() {
        tokenProvider.clearTokens()
        onTokenExpired?.invoke()
    }

    companion object {
        private const val UNAUTHORIZED = 401
        private const val MAX_RETRY = 2
        private const val AUTHORIZATION = "Authorization"
        private const val TOKEN_PREFIX = "Bearer"
        private const val SUCCESS_CODE = "CO000"
        private const val AUTO_LOGIN_HEADER = "Auto-Login"
    }
}
