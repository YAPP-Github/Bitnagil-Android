package com.threegap.bitnagil.data.auth.repositoryimpl

import com.threegap.bitnagil.data.auth.datasource.AuthRemoteDataSource
import com.threegap.bitnagil.data.auth.mapper.toDomain
import com.threegap.bitnagil.data.auth.model.request.LoginRequestDto
import com.threegap.bitnagil.domain.auth.model.AuthSession
import com.threegap.bitnagil.domain.auth.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
) : AuthRepository {
    override suspend fun login(socialAccessToken: String, socialType: String): Result<AuthSession> =
        authRemoteDataSource.login(socialAccessToken, LoginRequestDto(socialType))
            .map { it.toDomain() }
}
