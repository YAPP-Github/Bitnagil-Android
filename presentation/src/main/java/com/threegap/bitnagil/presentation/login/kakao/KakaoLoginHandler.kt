package com.threegap.bitnagil.presentation.login.kakao

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken

interface KakaoLoginHandler {
    fun login(
        context: Context,
        onResult: (OAuthToken?, Throwable?) -> Unit,
    )

    fun accountLogin(
        context: Context,
        onResult: (OAuthToken?, Throwable?) -> Unit,
    )
}
