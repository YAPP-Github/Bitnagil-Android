package com.threegap.bitnagil.presentation.login

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.threegap.bitnagil.domain.auth.usecase.LoginUseCase
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviViewModel
import com.threegap.bitnagil.presentation.login.model.LoginIntent
import com.threegap.bitnagil.presentation.login.model.LoginSideEffect
import com.threegap.bitnagil.presentation.login.model.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
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
            is LoginIntent.SetLoading -> {
                state.copy(isLoading = intent.isLoading)
            }

            is LoginIntent.LoginSuccess -> {
                sendSideEffect(
                    if (intent.isGuest) {
                        LoginSideEffect.NavigateToTermsAgreement
                    } else {
                        LoginSideEffect.NavigateToHome
                    },
                )
                state.copy(
                    isGuest = intent.isGuest,
                    isLoading = false,
                )
            }

            is LoginIntent.KakaoTalkLoginCancel -> {
                state.copy(isLoading = false)
            }

            is LoginIntent.LoginFailure -> {
                state.copy(isLoading = false)
            }
        }

    fun kakaoLogin(token: OAuthToken?, error: Throwable?) {
        viewModelScope.launch {
            sendIntent(LoginIntent.SetLoading(true))
            when {
                token != null -> {
                    processKakaoLoginSuccess(token)
                }

                error != null -> {
                    Log.e("KakaoLogin", "카카오 로그인 실패", error)
                    sendIntent(LoginIntent.LoginFailure)
                }
            }
        }
    }

    private suspend fun processKakaoLoginSuccess(token: OAuthToken) {
        loginUseCase(
            socialAccessToken = token.accessToken,
            socialType = "KAKAO",
        ).fold(
            onSuccess = {
                val isGuest = it.role.isGuest()
                sendIntent(LoginIntent.LoginSuccess(isGuest = isGuest))
            },
            onFailure = { e ->
                sendIntent(LoginIntent.LoginFailure)
                Log.e("Login", "${e.message}")
            },
        )
    }
}
