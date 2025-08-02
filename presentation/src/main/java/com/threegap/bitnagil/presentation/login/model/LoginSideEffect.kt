package com.threegap.bitnagil.presentation.login.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviSideEffect

sealed interface LoginSideEffect : MviSideEffect {
    data object RequestKakaoAccountLogin : LoginSideEffect
    data object NavigateToHome : LoginSideEffect
    data object NavigateToTermsAgreement : LoginSideEffect
}
