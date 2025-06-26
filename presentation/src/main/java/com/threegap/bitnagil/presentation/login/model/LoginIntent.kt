package com.threegap.bitnagil.presentation.login.model

import com.kakao.sdk.auth.model.OAuthToken
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviIntent

sealed class LoginIntent : MviIntent {
    data class OnKakaoLoginClick(val onKakaoTalkLoginAvailable: Boolean) : LoginIntent()

    data class OnKakaoLoginResult(
        val token: OAuthToken?,
        val error: Throwable?,
    ) : LoginIntent()
}
