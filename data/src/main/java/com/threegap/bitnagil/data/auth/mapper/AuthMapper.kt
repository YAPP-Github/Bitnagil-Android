package com.threegap.bitnagil.data.auth.mapper

import com.threegap.bitnagil.data.auth.model.response.LoginResponseDto
import com.threegap.bitnagil.domain.auth.model.AuthSession

internal fun LoginResponseDto.toDomain() = AuthSession(
    accessToken = accessToken,
    refreshToken = refreshToken,
    role = role,
)
