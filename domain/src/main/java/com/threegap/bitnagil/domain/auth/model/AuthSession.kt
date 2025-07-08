package com.threegap.bitnagil.domain.auth.model

data class AuthSession(
    val accessToken: String,
    val refreshToken: String,
    val role: String,
)
