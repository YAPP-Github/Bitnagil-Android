package com.threegap.bitnagil.data.auth.datasourceimpl

import com.threegap.bitnagil.data.auth.datasource.AuthRemoteDataSource
import com.threegap.bitnagil.data.auth.model.request.LoginRequestDto
import com.threegap.bitnagil.data.auth.model.response.LoginResponseDto
import com.threegap.bitnagil.data.auth.service.AuthService
import com.threegap.bitnagil.data.common.safeApiCall
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService,
) : AuthRemoteDataSource {
    override suspend fun login(socialAccessToken: String, loginRequestDto: LoginRequestDto): Result<LoginResponseDto> =
        safeApiCall {
            authService.postLogin(socialAccessToken, loginRequestDto)
        }
}
