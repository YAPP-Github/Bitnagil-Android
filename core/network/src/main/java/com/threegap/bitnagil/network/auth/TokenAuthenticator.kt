package com.threegap.bitnagil.network.auth

import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor() : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code != UNAUTHORIZED) return null
        if (responseCount(response) >= MAX_RETRY) return null

        // 재발급 api 연결 시 수정 예정입니다.(현재 코드는 임시)
        val newAccessToken = runBlocking {}

        return response.request.newBuilder()
            .header(AUTHORIZATION, "$BEARER $newAccessToken")
            .build()
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

    companion object {
        private const val UNAUTHORIZED = 401
        private const val MAX_RETRY = 2
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer"
    }
}
