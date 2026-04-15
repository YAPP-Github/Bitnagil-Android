package com.threegap.bitnagil.data.auth.datasourceimpl

import com.threegap.bitnagil.data.auth.datasource.AuthRemoteDataSource
import com.threegap.bitnagil.data.auth.model.request.LoginRequest
import com.threegap.bitnagil.data.auth.model.request.TermsAgreementRequest
import com.threegap.bitnagil.data.auth.model.request.WithdrawalReasonRequest
import com.threegap.bitnagil.data.auth.model.response.LoginResponse
import com.threegap.bitnagil.data.auth.service.AuthService
import com.threegap.bitnagil.data.auth.service.LoginService
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService,
    private val loginService: LoginService,
) : AuthRemoteDataSource {

    override suspend fun login(socialAccessToken: String, loginRequest: LoginRequest): Result<LoginResponse> =
        loginService.postLogin(socialAccessToken, loginRequest)

    override suspend fun submitAgreement(termsAgreementRequest: TermsAgreementRequest): Result<Unit> =
        authService.submitAgreement(termsAgreementRequest)

    override suspend fun logout(): Result<Unit> =
        authService.postLogout()

    override suspend fun withdrawal(reason: String): Result<Unit> =
        authService.postWithdrawal(WithdrawalReasonRequest(reason))

    override suspend fun reissueToken(refreshToken: String): Result<LoginResponse> =
        loginService.postReissueToken(refreshToken)
}
