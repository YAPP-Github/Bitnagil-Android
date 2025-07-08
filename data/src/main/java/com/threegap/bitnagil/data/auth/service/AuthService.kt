package com.threegap.bitnagil.data.auth.service

import com.threegap.bitnagil.data.auth.model.request.LoginRequestDto
import com.threegap.bitnagil.data.auth.model.response.LoginResponseDto
import com.threegap.bitnagil.network.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("/api/v1/auth/login")
    suspend fun postLogin(
        @Header("SocialAccessToken") socialAccessToken: String,
        @Body loginRequestDto: LoginRequestDto,
    ): BaseResponse<LoginResponseDto>
}
