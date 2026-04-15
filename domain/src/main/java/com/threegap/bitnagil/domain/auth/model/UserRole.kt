package com.threegap.bitnagil.domain.auth.model

import kotlinx.serialization.Serializable

@Serializable
enum class UserRole {
    USER,
    GUEST,
    ONBOARDING,
    WITHDRAWN,
    UNKNOWN,
    ;
}
