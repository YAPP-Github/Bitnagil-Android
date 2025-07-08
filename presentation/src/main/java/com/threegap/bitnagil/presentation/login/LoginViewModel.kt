package com.threegap.bitnagil.presentation.login

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.threegap.bitnagil.domain.auth.usecase.LoginUseCase
import com.threegap.bitnagil.domain.error.model.BitnagilError
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviViewModel
import com.threegap.bitnagil.presentation.login.model.LoginIntent
import com.threegap.bitnagil.presentation.login.model.LoginSideEffect
import com.threegap.bitnagil.presentation.login.model.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val loginUseCase: LoginUseCase,
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
                        loginUseCase(
                            socialAccessToken = intent.token.accessToken,
                            socialType = "KAKAO",
                        ).fold(
                            onSuccess = {
                                Log.i("Login", "서버 로그인 성공")
                            },
                            onFailure = { exception ->
                                if (exception is BitnagilError) {
                                    Log.e("Login", "${exception.code} ${exception.message}")
                                }
                                Log.e("Login", "${exception.message}")
                            },
                        )
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
