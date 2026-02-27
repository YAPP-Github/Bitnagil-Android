package com.threegap.bitnagil.presentation.screen.login.contract

sealed interface LoginSideEffect {
    data object NavigateToHome : LoginSideEffect
    data object NavigateToTermsAgreement : LoginSideEffect
}
