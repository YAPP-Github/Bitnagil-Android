package com.threegap.bitnagil.presentation.login.kakao

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

object KakaoLoginHandlerImpl : KakaoLoginHandler {
    private val client = UserApiClient.instance

    override fun login(
        context: Context,
        onResult: (OAuthToken?, Throwable?) -> Unit,
    ) {
        if (client.isKakaoTalkLoginAvailable(context)) {
            client.loginWithKakaoTalk(context, callback = onResult)
        } else {
            client.loginWithKakaoAccount(context, callback = onResult)
        }
    }

    override fun accountLogin(
        context: Context,
        onResult: (OAuthToken?, Throwable?) -> Unit,
    ) {
        client.loginWithKakaoAccount(context, callback = onResult)
    }
}
