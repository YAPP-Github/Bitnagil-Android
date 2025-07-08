package com.threegap.bitnagil.data.auth.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    @SerialName("socialType")
    val socialType: String,
)
