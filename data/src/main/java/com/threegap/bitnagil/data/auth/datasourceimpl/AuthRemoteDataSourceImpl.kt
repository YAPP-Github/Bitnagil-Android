package com.threegap.bitnagil.data.auth.datasourceimpl

import com.threegap.bitnagil.data.auth.datasource.AuthRemoteDataSource
import com.threegap.bitnagil.data.auth.model.request.LoginRequest
import com.threegap.bitnagil.data.auth.model.request.TermsAgreementRequest
import com.threegap.bitnagil.data.auth.model.request.WithdrawalReasonRequest
import com.threegap.bitnagil.data.auth.model.response.LoginResponse
import com.threegap.bitnagil.data.auth.service.AuthService
import com.threegap.bitnagil.data.auth.service.LoginService
import com.threegap.bitnagil.data.common.safeApiCall
import com.threegap.bitnagil.data.common.safeUnitApiCall
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService,
    private val loginService: LoginService,
) : AuthRemoteDataSource {
    override suspend fun login(socialAccessToken: String, loginRequest: LoginRequest): Result<LoginResponse> =
        safeApiCall {
            loginService.postLogin(socialAccessToken, loginRequest)
        }

    override suspend fun submitAgreement(termsAgreementRequest: TermsAgreementRequest): Result<Unit> =
        safeUnitApiCall {
            authService.submitAgreement(termsAgreementRequest)
        }

    override suspend fun logout(): Result<Unit> =
        safeUnitApiCall {
            authService.postLogout()
        }

    override suspend fun withdrawal(reason: String): Result<Unit> =
        safeUnitApiCall {
            authService.postWithdrawal(WithdrawalReasonRequest(reason))
        }

    override suspend fun reissueToken(refreshToken: String): Result<LoginResponse> =
        safeApiCall {
            loginService.postReissueToken(refreshToken)
        }
}
