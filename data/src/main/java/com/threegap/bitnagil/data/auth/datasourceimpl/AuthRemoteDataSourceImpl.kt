package com.threegap.bitnagil.data.auth.datasourceimpl

import com.threegap.bitnagil.data.auth.datasource.AuthRemoteDataSource
import com.threegap.bitnagil.data.auth.model.request.LoginRequestDto
import com.threegap.bitnagil.data.auth.model.request.TermsAgreementRequestDto
import com.threegap.bitnagil.data.auth.model.response.LoginResponseDto
import com.threegap.bitnagil.data.auth.service.AuthService
import com.threegap.bitnagil.data.common.safeApiCall
import com.threegap.bitnagil.data.common.safeUnitApiCall
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService,
) : AuthRemoteDataSource {
    override suspend fun login(socialAccessToken: String, loginRequestDto: LoginRequestDto): Result<LoginResponseDto> =
        safeApiCall {
            authService.postLogin(socialAccessToken, loginRequestDto)
        }

    override suspend fun submitAgreement(termsAgreementRequestDto: TermsAgreementRequestDto): Result<Unit> =
        safeApiCall {
            authService.submitAgreement(termsAgreementRequestDto)
        }

    override suspend fun logout(): Result<Unit> =
        safeUnitApiCall {
            authService.postLogout()
        }


    override suspend fun withdrawal(): Result<Unit> =
        safeUnitApiCall {
            authService.postWithdrawal()
        }

}
