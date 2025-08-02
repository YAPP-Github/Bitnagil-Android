package com.threegap.bitnagil.domain.auth.model

enum class UserRole {
    USER,
    GUEST,
    ;

    fun isGuest() = this == GUEST

    companion object {
        fun from(value: String): UserRole =
            when (value) {
                "USER" -> USER
                "GUEST" -> GUEST
                else -> throw IllegalArgumentException("Unknown role: $value")
            }
    }
}
