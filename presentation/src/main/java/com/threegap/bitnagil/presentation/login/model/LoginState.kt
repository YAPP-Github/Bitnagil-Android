package com.threegap.bitnagil.presentation.login.model

data class LoginState(
    val isLoading: Boolean,
    val isGuest: Boolean,
) {
    companion object {
        val INIT = LoginState(
            isLoading = false,
            isGuest = false,
        )
    }
}
