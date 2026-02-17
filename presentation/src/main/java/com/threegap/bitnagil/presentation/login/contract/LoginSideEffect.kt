package com.threegap.bitnagil.presentation.login.contract

sealed interface LoginSideEffect {
    data object NavigateToHome : LoginSideEffect
    data object NavigateToTermsAgreement : LoginSideEffect
}
