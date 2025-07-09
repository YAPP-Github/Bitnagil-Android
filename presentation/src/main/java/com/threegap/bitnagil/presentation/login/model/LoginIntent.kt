package com.threegap.bitnagil.presentation.login.model

import com.threegap.bitnagil.presentation.common.mviviewmodel.MviIntent

sealed class LoginIntent : MviIntent {
    data class OnKakaoLoginClick(val onKakaoTalkLoginAvailable: Boolean) : LoginIntent()
    data class SetLoading(val isLoading: Boolean) : LoginIntent()
    data class LoginSuccess(val isGuest: Boolean) : LoginIntent()
    data object KakaoTalkLoginCancel : LoginIntent()
    data object LoginFailure : LoginIntent()
}
