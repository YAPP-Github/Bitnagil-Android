package com.threegap.bitnagil.datastore.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthToken(
    val accessToken: String? = null,
    val refreshToken: String? = null,
)
