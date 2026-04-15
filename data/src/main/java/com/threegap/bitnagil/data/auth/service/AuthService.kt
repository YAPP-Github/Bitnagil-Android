package com.threegap.bitnagil.data.auth.service

import com.threegap.bitnagil.data.auth.model.request.TermsAgreementRequest
import com.threegap.bitnagil.data.auth.model.request.WithdrawalReasonRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/api/v1/auth/agreements")
    suspend fun submitAgreement(
        @Body termsAgreementRequest: TermsAgreementRequest,
    ): Result<Unit>

    @POST("/api/v1/auth/withdrawal")
    suspend fun postWithdrawal(@Body request: WithdrawalReasonRequest): Result<Unit>

    @POST("/api/v1/auth/logout")
    suspend fun postLogout(): Result<Unit>
}
