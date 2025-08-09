package com.threegap.bitnagil.data.auth.service

import com.threegap.bitnagil.data.auth.model.request.LoginRequestDto
import com.threegap.bitnagil.data.auth.model.request.TermsAgreementRequestDto
import com.threegap.bitnagil.data.auth.model.response.LoginResponseDto
import com.threegap.bitnagil.network.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthService {
    @POST("/api/v1/auth/login")
    @Headers("No-Service-Token: true")
    suspend fun postLogin(
        @Header("SocialAccessToken") socialAccessToken: String,
        @Body loginRequestDto: LoginRequestDto,
    ): BaseResponse<LoginResponseDto>

    @POST("/api/v1/auth/agreements")
    suspend fun submitAgreement(
        @Body termsAgreementRequestDto: TermsAgreementRequestDto,
    ): BaseResponse<Unit>

    @POST("/api/v1/auth/withdrawal")
    suspend fun postWithdrawal(): BaseResponse<Unit>

    @POST("/api/v1/auth/logout")
    suspend fun postLogout(): BaseResponse<Unit>

    @POST("/api/v1/auth/token/reissue")
    @Headers("No-Service-Token: true")
    suspend fun postReissueToken(
        @Header("Refresh-Token") refreshToken: String,
    ): BaseResponse<LoginResponseDto>
}
