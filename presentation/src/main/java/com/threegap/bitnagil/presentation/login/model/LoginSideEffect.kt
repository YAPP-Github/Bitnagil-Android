package com.threegap.bitnagil.presentation.login.model

sealed interface LoginSideEffect {
    data object NavigateToHome : LoginSideEffect
    data object NavigateToTermsAgreement : LoginSideEffect
}
