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
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
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
                intent { handleKakaoLoginResult(intent) }
                null
            }
        }

    private suspend fun SimpleSyntax<LoginState, LoginSideEffect>.handleKakaoLoginResult(
        intent: LoginIntent.OnKakaoLoginResult,
    ) {
        reduce { state.copy(isLoading = true) }

        when {
            intent.token != null -> {
                processBitnagilLogin(
                    socialAccessToken = intent.token.accessToken,
                    socialType = "KAKAO",
                )
            }

            intent.error is ClientError && intent.error.reason == ClientErrorCause.Cancelled -> {
                Log.e("KakaoLogin", "로그인 취소", intent.error)
                reduce { state.copy(isLoading = false) }
            }

            intent.error != null -> {
                Log.e("KakaoLogin", "로그인 실패", intent.error)
                reduce { state.copy(isLoading = false) }
            }
        }
    }

    private suspend fun SimpleSyntax<LoginState, LoginSideEffect>.processBitnagilLogin(
        socialAccessToken: String,
        socialType: String,
    ) {
        loginUseCase(
            socialAccessToken = socialAccessToken,
            socialType = socialType,
        ).fold(
            onSuccess = {
                val isGuest = it.role.isGuest()

                reduce {
                    state.copy(
                        isGuest = isGuest,
                        isLoading = false,
                    )
                }

                sendSideEffect(
                    if (isGuest) {
                        LoginSideEffect.NavigateToTermsOfService
                    } else {
                        LoginSideEffect.NavigateToHome
                    },
                )
            },
            onFailure = { e ->
                reduce { state.copy(isLoading = false) }

                if (e is BitnagilError) {
                    Log.e("Login", "${e.code} ${e.message}")
                }
                Log.e("Login", "${e.message}")
            },
        )
    }
}
