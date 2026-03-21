package com.threegap.bitnagil.data.auth.service

import com.threegap.bitnagil.data.auth.model.request.LoginRequest
import com.threegap.bitnagil.data.auth.model.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginService {
    @POST("/api/v1/auth/login")
    suspend fun postLogin(
        @Header("SocialAccessToken") socialAccessToken: String,
        @Body loginRequest: LoginRequest,
    ): Result<LoginResponse>

    @POST("/api/v1/auth/token/reissue")
    suspend fun postReissueToken(
        @Header("Refresh-Token") refreshToken: String,
    ): Result<LoginResponse>
}
