package com.threegap.bitnagil.data.auth.service

import com.threegap.bitnagil.data.auth.model.request.LoginRequest
import com.threegap.bitnagil.data.auth.model.request.TermsAgreementRequest
import com.threegap.bitnagil.data.auth.model.request.WithdrawalReasonRequest
import com.threegap.bitnagil.data.auth.model.response.LoginResponse
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
        @Body loginRequest: LoginRequest,
    ): BaseResponse<LoginResponse>

    @POST("/api/v1/auth/agreements")
    suspend fun submitAgreement(
        @Body termsAgreementRequest: TermsAgreementRequest,
    ): BaseResponse<Unit>

    @POST("/api/v1/auth/withdrawal")
    suspend fun postWithdrawal(@Body request: WithdrawalReasonRequest): BaseResponse<Unit>

    @POST("/api/v1/auth/logout")
    suspend fun postLogout(): BaseResponse<Unit>

    @POST("/api/v1/auth/token/reissue")
    @Headers("No-Service-Token: true", "Auto-Login: true")
    suspend fun postReissueToken(
        @Header("Refresh-Token") refreshToken: String,
    ): BaseResponse<LoginResponse>
}
