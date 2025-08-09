package com.threegap.bitnagil.domain.auth.model

enum class UserRole {
    USER,
    GUEST,
    ONBOARDING,
    WITHDRAWN,
    UNKNOWN,
    ;

    fun isGuest() = this == GUEST

    companion object {
        fun fromString(value: String): UserRole =
            when (value) {
                "USER" -> USER
                "GUEST" -> GUEST
                "ONBOARDING" -> ONBOARDING
                "WITHDRAWN" -> WITHDRAWN
                else -> UNKNOWN
            }
    }
}
