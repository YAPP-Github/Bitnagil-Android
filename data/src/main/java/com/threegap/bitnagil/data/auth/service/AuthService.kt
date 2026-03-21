package com.threegap.bitnagil.data.auth.service

import com.threegap.bitnagil.data.auth.model.request.TermsAgreementRequest
import com.threegap.bitnagil.data.auth.model.request.WithdrawalReasonRequest
import com.threegap.bitnagil.network.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/api/v1/auth/agreements")
    suspend fun submitAgreement(
        @Body termsAgreementRequest: TermsAgreementRequest,
    ): BaseResponse<Unit>

    @POST("/api/v1/auth/withdrawal")
    suspend fun postWithdrawal(@Body request: WithdrawalReasonRequest): BaseResponse<Unit>

    @POST("/api/v1/auth/logout")
    suspend fun postLogout(): BaseResponse<Unit>
}
