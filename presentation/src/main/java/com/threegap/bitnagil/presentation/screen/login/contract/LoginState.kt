package com.threegap.bitnagil.presentation.screen.login.contract

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
