package com.threegap.bitnagil.data.auth.model.response

import com.threegap.bitnagil.domain.auth.model.UserRole
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("role")
    val role: UserRole,
)
