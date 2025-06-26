package com.threegap.bitnagil.presentation.login

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviViewModel
import com.threegap.bitnagil.presentation.login.model.LoginIntent
import com.threegap.bitnagil.presentation.login.model.LoginSideEffect
import com.threegap.bitnagil.presentation.login.model.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val savedStateHandle: SavedStateHandle,
    ) : MviViewModel<LoginState, LoginSideEffect, LoginIntent>(
            initState = LoginState(),
            savedStateHandle = savedStateHandle,
        ) {
        override suspend fun SimpleSyntax<LoginState, LoginSideEffect>.reduceState(
            intent: LoginIntent,
            state: LoginState,
        ): LoginState? =
            when (intent) {
                is LoginIntent.OnKakaoLoginClick -> {
                    if (!intent.onKakaoTalkLoginAvailable) {
                        sendSideEffect(LoginSideEffect.RequestKakaoAccountLogin)
                    } else {
                        sendSideEffect(LoginSideEffect.RequestKakaoTalkLogin)
                    }
                    null
                }

                is LoginIntent.OnKakaoLoginResult -> {
                    when {
                        intent.token != null -> {
                            Log.i("KakaoLogin", "로그인 성공 ${intent.token.accessToken}")
                            UserApiClient.instance.me { user, error ->
                                if (error != null) {
                                    Log.e("KakaoLogin", "사용자 정보 요청 실패", error)
                                } else if (user != null) {
                                    Log.i(
                                        "KakaoLogin",
                                        "사용자 정보 요청 성공" +
                                            "\n이메일: ${user.kakaoAccount?.email}" +
                                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}",
                                    )
                                }
                            }
                        }

                        intent.error is ClientError && intent.error.reason == ClientErrorCause.Cancelled -> {
                            Log.e("KakaoLogin", "로그인 취소", intent.error)
                        }

                        intent.error != null -> {
                            Log.e("KakaoLogin", "로그인 실패", intent.error)
                        }
                    }
                    null
                }
            }
    }
