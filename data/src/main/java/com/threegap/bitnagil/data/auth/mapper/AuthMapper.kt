package com.threegap.bitnagil.data.auth.mapper

import com.threegap.bitnagil.data.auth.model.response.LoginResponseDto
import com.threegap.bitnagil.domain.auth.model.AuthSession
import com.threegap.bitnagil.domain.auth.model.UserRole

internal fun LoginResponseDto.toDomain() = AuthSession(
    accessToken = accessToken,
    refreshToken = refreshToken,
    role = UserRole.from(role),
)
